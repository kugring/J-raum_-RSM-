package com.kugring.back.service.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kugring.back.dto.object.OrderItemObject;
import com.kugring.back.dto.object.OrderItemOptionObject;
import com.kugring.back.dto.request.order.FilterOrderListRequestDto;
import com.kugring.back.dto.request.order.PatchOrderListRequestDto;
import com.kugring.back.dto.request.order.PostOrderListRequestDto;
import com.kugring.back.dto.request.order.PutOrderListRequestDto;
import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.dto.response.order.FilterOrderListResponseDto;
import com.kugring.back.dto.response.order.PatchOrderListResponseDto;
import com.kugring.back.dto.response.order.PostOrderListResponseDto;
import com.kugring.back.dto.response.order.PutOrderListResponseDto;
import com.kugring.back.entity.MenuEntity;
import com.kugring.back.entity.OptionEntity;
import com.kugring.back.entity.OrderItemEntity;
import com.kugring.back.entity.OrderItemOptionEntity;
import com.kugring.back.entity.OrderListEntity;
import com.kugring.back.entity.UserEntity;
import com.kugring.back.repository.MenuRepository;
import com.kugring.back.repository.OptionRepository;
import com.kugring.back.repository.OrderListRepository;
import com.kugring.back.repository.UserRepository;
import com.kugring.back.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImplement implements OrderService {



    private final OrderListRepository orderListRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OptionRepository optionRepository;

    @Override
    @Transactional
    public ResponseEntity<? super PostOrderListResponseDto> postOrderList(PostOrderListRequestDto dto) {

        try {
            // 주문요청자의 회원존재 유무 확인
            UserEntity userEntity = userRepository.findByUserId(dto.getUserId());
            if(userEntity == null) return PostOrderListResponseDto.noExistUser();
            // 메뉴 ID들 추출
            List<Integer> menuIds = dto.getOrderItems().stream().map(OrderItemObject::getMenuId).collect(Collectors.toList());
            // 해당 메뉴 ID들이 모두 존재하는지 확인
            if (menuRepository.countByMenuIdIn(menuIds) != menuIds.size()) return PostOrderListResponseDto.noExistMenu();
            // 옵션 코드들 추출
            List<Integer> optionIds = dto.getOrderItems().stream().flatMap(orderItem -> orderItem.getOrderItemOptions().stream()).map(OrderItemOptionObject::getOptionId).collect(Collectors.toList());
            // 해당 옵션 코드들이 모두 존재하는지 확인
            if (optionRepository.countByOptionIdIn(optionIds) != optionIds.size()) return PostOrderListResponseDto.noExistOption();
            
            // OrderListEntity 생성
            OrderListEntity orderList = new OrderListEntity();

            // OrderItemEntity 리스트 생성 및 추가
            List<OrderItemEntity> orderItems = dto.getOrderItems().stream().map(itemDto -> {
                // 등록할 아이템 생성
                OrderItemEntity orderItem = new OrderItemEntity();
                // OrderList와 OrderItem 연결
                orderItem.setOrderList(orderList); 
                // 아이템 메뉴 Entity 가져오기
                MenuEntity menuEntity = menuRepository.findByMenuId(itemDto.getMenuId());
                // 아이템 메뉴 Set
                orderItem.setMenu(menuEntity);
                // 아이템 수량
                orderItem.setOrderItemQuantity(itemDto.getOrderItemQuantity());
                // OrderItemOptionEntity 리스트 생성 및 추가
                List<OrderItemOptionEntity> orderItemOptions = itemDto.getOrderItemOptions().stream().map(optionDto -> {
                    // 등록할 아이템의 옵션 생성
                    OrderItemOptionEntity orderItemOption =  new OrderItemOptionEntity();
                    // OrderItem 과 OrderItemOption 연결
                    orderItemOption.setOrderItem(orderItem);
                    // 옵션 Entity 가져오기
                    OptionEntity optionEntity = optionRepository.findByOptionId(optionDto.getOptionId());
                    // 옵션 코드 Set
                    orderItemOption.setOption(optionEntity);
                    // 옵션 수량 Set
                    orderItemOption.setOptionQuantity(optionDto.getOptionQuantity());
                    // 가공된 옵션 데이터 반환
                    return orderItemOption;
                }).collect(Collectors.toList());
                // 가공된 옵션 리스트를 아이템의 필드에 Set
                orderItem.setOrderItemOptions(orderItemOptions);
                return orderItem;
            }).collect(Collectors.toList());


            int totalPrice = orderItems.stream()
                .mapToInt(orderItem -> {
                    int menuPrice = orderItem.getMenu().getMenuPrice();
                    int itemQuantity = orderItem.getOrderItemQuantity();
                    
                    // 기본 메뉴 가격에 옵션 가격의 총합을 추가합니다.
                    int optionTotalPrice = orderItem.getOrderItemOptions().stream()
                        .mapToInt(optionItem -> optionItem.getOptionQuantity() * optionItem.getOption().getOptionPrice())
                        .sum();
                    
                    return (menuPrice + optionTotalPrice) * itemQuantity;
                }).sum();

            // 잔액 확인
            int updatedPoint = userEntity.getPoint() - totalPrice;

            // 포인트가 음수가 되지 않도록 설정
            if (updatedPoint < 0) return PostOrderListResponseDto.insufficientBlance();
            // 잔여금 저장
            userEntity.setPoint(updatedPoint);
            
            // 주문_아이템 담기
            orderList.setOrderItems(orderItems);
            // 주문자 등록
            orderList.setUser(userEntity);
            // 주문 날짜 등록
            orderList.setCreateOrderDate(LocalDateTime.now());
            // 주문 상태 등록
            orderList.setOrderStatus("대기");
            // 주문 결제 방법 등록
            orderList.setPayMethod(dto.getPayMethod());

            // 저장
            userRepository.save(userEntity);
            orderListRepository.save(orderList);

        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseDto.databaseError();
        }

        return PostOrderListResponseDto.success();

    }

    @Override
    public ResponseEntity<? super FilterOrderListResponseDto> filterOrderList(FilterOrderListRequestDto dto) {

        List<OrderListEntity> orderListEntities = new ArrayList<>();

        try {

            String userId = dto.getUserId();
            String orderStatus = dto.getOrderStatus();
            LocalDateTime startCreateDate = dto.getStartCreateDate();
            LocalDateTime endCreateDate = dto.getEndCreateDate();
            LocalDateTime startCompleteDate = dto.getStartCompleteDate();
            LocalDateTime endCompleteDate = dto.getEndCompleteDate();

            orderListEntities = orderListRepository.findOrders(userId, orderStatus, startCreateDate, endCreateDate, startCompleteDate, endCompleteDate);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseDto.databaseError();
        }

        return FilterOrderListResponseDto.success(orderListEntities);
    }

    @Override
    @Transactional
    public ResponseEntity<? super PatchOrderListResponseDto> patchOrderList(Integer orderListId, PatchOrderListRequestDto dto) {

        try {

            // 주문리스트를 찾아옴
            OrderListEntity orderListEntity = orderListRepository.findByOrderListId(orderListId);

            // 해당 주문리스트가 없으면 예외처리
            if(orderListEntity == null) return PatchOrderListResponseDto.noExistOrder();

            // Dto에서 주문 상태 가져옴
            String orderStatus = dto.getOrderStatus();

            // 이전 주문 상태 가져옴
            String preOrderStatus = orderListEntity.getOrderStatus();

            // 주문 상태 오류라면 예외처리
            Set<String> validStatuses = Set.of("대기", "완료", "취소");
            if (!validStatuses.contains(orderStatus))  return PatchOrderListResponseDto.noExistOrderStatus();

            // 주문자 정보 가져옴
            UserEntity userEntity = orderListEntity.getUser();

            // 완료 상태에서 대기로 변경 시 완료시간 초기화
            if (preOrderStatus.equals("완료") && orderStatus.equals("대기")) {
                orderListEntity.setCompleteOrderDate(null);
            }
            // 대기 상태에서 완료로 변경 시 완료시간 작성
            else if (preOrderStatus.equals("대기") && orderStatus.equals("완료")) {
                orderListEntity.setCompleteOrderDate(LocalDateTime.now());
            }
            // 포인트 계산 및 처리
            else if ((preOrderStatus.equals("대기") && orderStatus.equals("취소")) ||
                (preOrderStatus.equals("취소") && (orderStatus.equals("대기") || orderStatus.equals("완료")))) {
                orderListEntity.setCompleteOrderDate(LocalDateTime.now());
                int totalPrice = orderListEntity.getOrderItems().stream()
                    .mapToInt(orderItem -> {
                        int menuPrice = orderItem.getMenu().getMenuPrice();
                        int optionTotalPrice = orderItem.getOrderItemOptions().stream()
                            .mapToInt(optionItem -> optionItem.getOptionQuantity() * optionItem.getOption().getOptionPrice())
                            .sum();
                        return (menuPrice + optionTotalPrice) * orderItem.getOrderItemQuantity();
                    }).sum();
                
                if (orderStatus.equals("취소")) {
                    userEntity.pointCharge(totalPrice);  // 포인트 롤백
                } else {
                    userEntity.pointPay(totalPrice);     // 포인트 결제
                }
            } else {
                // 상태가 이상하면 예외처리
                return PatchOrderListResponseDto.noExistOrderStatus();
            }

            // 주문 상태를 변경해줌
            orderListEntity.setOrderStatus(orderStatus);
            orderListRepository.save(orderListEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchOrderListResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PutOrderListResponseDto> putOrderList(Integer orderListId, PutOrderListRequestDto dto) {


        try {

            // OrderListEntity 조회
            OrderListEntity orderList = orderListRepository.findByOrderListId(orderListId);

            // 예외처리_(주문리스트ID)
            if(orderList == null) return PutOrderListResponseDto.orderFail();

            // 예외처리_(포인트결제가 아닌 경우)
            if(!orderList.getPayMethod().equals("포인트결제")) return PutOrderListResponseDto.orderFail();

            // 예외처리_(대기상태가 아닌 경우)
            if(!orderList.getOrderStatus().equals("대기")) return PutOrderListResponseDto.orderFail();

            // 예외처리_(메뉴ID)
            List<Integer> menuIds = dto.getOrderItems().stream().map(OrderItemObject::getMenuId).collect(Collectors.toList());
            if (menuRepository.countByMenuIdIn(menuIds) != menuIds.size()) return PutOrderListResponseDto.noExistMenu();

            // 예외처리_(옵션코드) // todo: 여기에 문제 있음
            List<Integer> optionIds = dto.getOrderItems().stream().flatMap(orderItem -> orderItem.getOrderItemOptions().stream())
                    .map(OrderItemOptionObject::getOptionId).collect(Collectors.toList());
            if (optionRepository.countByOptionIdIn(optionIds) != optionIds.size()) return PutOrderListResponseDto.noExistOption();


            // 주문자 가져오기
            UserEntity userEntity = orderList.getUser();

            // 기존 OrderItemEntity 리스트 가져오기
            List<OrderItemEntity> preOrderItems = orderList.getOrderItems();

            // 취소된 가격
            int rollBackPrice = preOrderItems.stream()
            .mapToInt(orderItem -> {
                int menuPrice = orderItem.getMenu().getMenuPrice();
                int itemQuantity = orderItem.getOrderItemQuantity();
                
                // 기본 메뉴 가격에 옵션 가격의 총합을 추가합니다.
                int optionTotalPrice = orderItem.getOrderItemOptions().stream()
                    .mapToInt(optionItem -> optionItem.getOptionQuantity() * optionItem.getOption().getOptionPrice())
                    .sum();
                
                return (menuPrice + optionTotalPrice) * itemQuantity;
            }).sum();

            // 포인트 롤백
            userEntity.pointCharge(rollBackPrice);

            // orderItems 리스트를 비우면 종속된 order_item 데이터도 삭제됩니다.
            orderList.getOrderItems().clear();

            // todo: 클린하고 저장을 해야 삭제되나?  결론: 안해도됨
            // orderListRepository.save(orderList);

            // OrderItemEntity 리스트 생성 및 추가
            List<OrderItemEntity> orderItems = dto.getOrderItems().stream().map(itemDto -> {
                // 등록할 아이템 생성
                OrderItemEntity orderItem = new OrderItemEntity();
                // OrderList와 OrderItem 연결
                orderItem.setOrderList(orderList); 
                // 아이템 메뉴 Entity 가져오기  // todo : menuId만 넣어도 혹시 알아서 저장해주나? // 결론: 알아서 넣어줌 // But: 메뉴 가겨을 알아내기 위해서 엔티티로 저장!
                MenuEntity menuEntity = menuRepository.findByMenuId(itemDto.getMenuId());
                // MenuEntity menuEntity = new MenuEntity();
                // menuEntity.setMenuId(itemDto.getMenuId());
                // 아이템 메뉴 Set
                orderItem.setMenu(menuEntity);
                // 아이템 수량
                orderItem.setOrderItemQuantity(itemDto.getOrderItemQuantity());
                // OrderItemOptionEntity 리스트 생성 및 추가
                List<OrderItemOptionEntity> orderItemOptions = itemDto.getOrderItemOptions().stream().map(optionDto -> {
                    // 등록할 아이템의 옵션 생성
                    OrderItemOptionEntity orderItemOption =  new OrderItemOptionEntity();
                    // OrderItem 과 OrderItemOption 연결
                    orderItemOption.setOrderItem(orderItem);
                    // 옵션 Entity 가져오기
                    OptionEntity optionEntity = optionRepository.findByOptionId(optionDto.getOptionId());
                    // 옵션 코드 Set
                    orderItemOption.setOption(optionEntity);
                    // 옵션 수량 Set
                    orderItemOption.setOptionQuantity(optionDto.getOptionQuantity());
                    // 가공된 옵션 데이터 반환
                    return orderItemOption;
                }).collect(Collectors.toList());
                // 가공된 옵션 리스트를 아이템의 필드에 Set
                orderItem.setOrderItemOptions(orderItemOptions);
                return orderItem;
            }).collect(Collectors.toList());


            int totalPrice = orderItems.stream()
                .mapToInt(orderItem -> {
                    int menuPrice = orderItem.getMenu().getMenuPrice();
                    int itemQuantity = orderItem.getOrderItemQuantity();

                    // 기본 메뉴 가격에 옵션 가격의 총합을 추가합니다.
                    int optionTotalPrice = orderItem.getOrderItemOptions().stream()
                        .mapToInt(optionItem -> optionItem.getOptionQuantity() * optionItem.getOption().getOptionPrice())
                        .sum();
                    
                    return (menuPrice + optionTotalPrice) * itemQuantity;
                }).sum();

            // 잔액 확인
            int updatedPoint = userEntity.getPoint() - totalPrice;

            // 포인트가 음수가 되지 않도록 설정
            if (updatedPoint < 0) return PostOrderListResponseDto.insufficientBlance();
            // 잔여금 저장
            userEntity.setPoint(updatedPoint);

            // 수정된_주문_아이템 담기
            orderList.getOrderItems().addAll(orderItems);
            // 수정된_주문자 등록
            orderList.setUser(userEntity);

            // 저장
            userRepository.save(userEntity);
            orderListRepository.save(orderList);

        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseDto.databaseError();
        }
        return PutOrderListResponseDto.success();
    }

}
