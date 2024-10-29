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
import com.kugring.back.repository.OrderItemOptionRepository;
import com.kugring.back.repository.OrderItemRepository;
import com.kugring.back.repository.OrderListRepository;
import com.kugring.back.repository.UserRepository;
import com.kugring.back.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImplement implements OrderService {



    private final OrderListRepository orderListRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemOptionRepository orderItemOptionRepository;
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
            List<String> optionCodes = dto.getOrderItems().stream().flatMap(orderItem -> orderItem.getOrderItemOptions().stream()).map(OrderItemOptionObject::getOptionCode).collect(Collectors.toList());
            // 해당 옵션 코드들이 모두 존재하는지 확인
            if (optionRepository.countByOptionCodeIn(optionCodes) != optionCodes.size()) return PostOrderListResponseDto.noExistOption();
            
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
                    OptionEntity optionEntity = optionRepository.findByOptionCode(optionDto.getOptionCode());
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

    // @Override
    // public ResponseEntity<? super FilterOrderListResponseDto> filterOrderList(FilterOrderListRequestDto dto) {

    //     List<OrderListEntity> orderListEntities = new ArrayList<>();

    //     try {

    //         String userId = dto.getUserId();
    //         String orderStatus = dto.getOrderStatus();
    //         LocalDateTime startCreateDate = dto.getStartCreateDate();
    //         LocalDateTime endCreateDate = dto.getEndCreateDate();
    //         LocalDateTime startCompleteDate = dto.getStartCompleteDate();
    //         LocalDateTime endCompleteDate = dto.getEndCompleteDate();

    //         // 회원정보가 없는 겨우
    //         if (userId != null && !userRepository.existsByUserId(userId))
    //             return FilterOrderListResponseDto.noExistUser();
    //         // 주문상태가 다른 경우
    //         if (orderStatus != null && !("대기".equals(orderStatus) || "완성".equals(orderStatus)))
    //             return FilterOrderListResponseDto.vaildationFail();
    //         orderListEntities =
    //                 orderListRepository.findOrders(userId, orderStatus, startCreateDate, endCreateDate, startCompleteDate, endCompleteDate);

    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         ResponseDto.databaseError();
    //     }

    //     return FilterOrderListResponseDto.success(orderListEntities);
    // }

    // @Override
    // @Transactional
    // public ResponseEntity<? super PatchOrderListResponseDto> patchOrderList(Integer orderListId, PatchOrderListRequestDto dto) {

    //     try {

    //         // 해당 주문리스트가 없으면 반환
    //         if (!orderListRepository.existsByOrderListId(orderListId)) {
    //             return PatchOrderListResponseDto.noExistOrder();
    //         }

    //         // 주문리스트를 찾아옴
    //         OrderListEntity orderListEntity = orderListRepository.findByOrderListId(orderListId);
    //         String orderStatus = dto.getOrderStatus();
    //         String preOrderStatus = orderListEntity.getOrderStatus();

    //         // 포인트 업데이트 로직 처리
    //         UserEntity user = userRepository.findByUserId(orderListEntity.getUser().getUserId());
    //         int totalPrice = calculateTotalPrice(orderListEntity);

    //         Set<String> validStatuses = Set.of("대기", "완료", "취소");

    //         if (!validStatuses.contains(orderStatus)) {
    //             return PatchOrderListResponseDto.noExistOrderStatus();
    //         }
    //         if (!preOrderStatus.equals("취소") && orderStatus.equals("취소")) {
    //             // 환불 처리
    //             updateUserPoints(user, totalPrice, true);
    //         } else if (preOrderStatus.equals("취소") && (orderStatus.equals("대기") || orderStatus.equals("완료"))) {
    //             // 결제 처리
    //             if ((user.getPoint() - totalPrice) < 0) {
    //                 return PatchOrderListResponseDto.insufficientBlance();
    //             }
    //             updateUserPoints(user, totalPrice, false);
    //         }

    //         // 주문 상태를 변경해줌
    //         orderListEntity.setOrderStatus(orderStatus);
    //         orderListRepository.save(orderListEntity);

    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         return ResponseDto.databaseError();
    //     }

    //     return PatchOrderListResponseDto.success();
    // }

    // @Override
    // public ResponseEntity<? super PutOrderListResponseDto> putOrderList(Integer orderListId, PutOrderListRequestDto dto) {


    //     try {

    //         // 예외처리_(주문리스트ID)
    //         if (!orderListRepository.existsByOrderListId(orderListId)) {
    //             return PutOrderListResponseDto.orderFail();
    //         }
    //         // 예외처리_(메뉴ID)
    //         List<Integer> menuIds = dto.getOrderItems().stream().map(OrderItem::getMenuId).collect(Collectors.toList());
    //         if (menuRepository.countByMenuIdIn(menuIds) != menuIds.size()) {
    //             return PutOrderListResponseDto.noExistMenu();
    //         }
    //         // 예외처리_(옵션코드)
    //         List<String> optionCodes = dto.getOrderItems().stream().flatMap(orderItem -> orderItem.getOrderItemOptions().stream())
    //                 .map(OrderItemOption::getOptionCode).collect(Collectors.toList());
    //         if (optionRepository.countByOptionCodeIn(optionCodes) != optionCodes.size()) {
    //             return PutOrderListResponseDto.noExistOption();
    //         }

    //         // OrderListEntity 조회
    //         OrderListEntity orderList = orderListRepository.findByOrderListId(orderListId);



    //         // todo: 원래는 기존데이터를 수정하고 새로운 값이 있는 경우 추가하는 식으로 하려 했는데 그냥 기존 데이터 다 날리고 새로운 데이터를 넣는 쪽으로 생각하는걸루!

    //         // OrderListEntity 조회

    //         // 기존 OrderItemEntity 리스트 가져오기
    //         List<OrderItemEntity> preOrderItems = orderList.getOrderItems();

    //         // 기존의 OrderItemEntity 및 OrderItemOptionEntity 삭제
    //         for (OrderItemEntity item : preOrderItems) {
    //             // 각 OrderItemEntity에 종속된 OrderItemOptionEntity 삭제
    //             List<OrderItemOptionEntity> options = item.getOrderItemOptions();
    //             options.forEach(option -> orderItemOptionRepository.delete(option)); // 옵션 삭제

    //             // OrderItemEntity 삭제
    //             orderItemRepository.delete(item);
    //         }



    //         // 새로운 OrderItemEntity 리스트를 OrderListEntity에 설정

    //         // 저장
    //         orderListRepository.save(orderList);


    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         ResponseDto.databaseError();
    //     }
    //     return PutOrderListResponseDto.success();
    // }

    // // 주문 항목의 가격 계산 메소드
    // private int calculateTotalPrice(OrderListEntity orderListEntity) {
    //     int totalPrice = 0;

    //     for (OrderItemEntity orderItemEntity : orderListEntity.getOrderItems()) {
    //         // 메뉴 가격 조회
    //         int menuPrice = menuRepository.findPriceByMenuId(orderItemEntity.getMenu().getMenuId()).orElseThrow(() -> new RuntimeException("Menu not found"));
    //         totalPrice += menuPrice * orderItemEntity.getOrderItemQuantity();

    //         // 옵션 가격 계산
    //     }
    //     return totalPrice;
    // }

    // // 포인트 업데이트 메소드
    // private void updateUserPoints(UserEntity user, int totalPrice, boolean isRefund) {
    //     if (isRefund) {
    //         user.setPoint(user.getPoint() + totalPrice); // 환불 시 포인트 추가
    //     } else {
    //         user.setPoint(user.getPoint() - totalPrice); // 결제 시 포인트 차감
    //     }
    //     userRepository.save(user);
    // }

}
