package com.kugring.back.dto.response.order;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kugring.back.common.ResponseCode;
import com.kugring.back.common.ResponseMessage;
import com.kugring.back.dto.object.OrderListItemObject;
import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.entity.OrderListEntity;

import lombok.Getter;

@Getter
public class FilterOrderListResponseDto extends ResponseDto {

  private List<OrderListItemObject> orderItems;

  private FilterOrderListResponseDto(List<OrderListEntity> orderListEntities) {
    super();
    this.orderItems = OrderListItemObject.getList(orderListEntities);
  }

  // 성공 응답
  public static ResponseEntity<FilterOrderListResponseDto> success(List<OrderListEntity> orderListEntities) {
    FilterOrderListResponseDto result = new FilterOrderListResponseDto(orderListEntities);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  // 실패 응답 (사용자 없음)
  public static ResponseEntity<ResponseDto> noExistUser() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
  }

  // 실패 응답 (주문 없음)
  public static ResponseEntity<ResponseDto> noExistOrder() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ORDER, ResponseMessage.NOT_EXISTED_ORDER);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }
}
