package com.kugring.back.dto.response.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kugring.back.common.ResponseCode;
import com.kugring.back.common.ResponseMessage;
import com.kugring.back.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class PatchOrderListResponseDto extends ResponseDto {

  private PatchOrderListResponseDto() {
    super();
  }

  // 성공 응답
  public static ResponseEntity<PatchOrderListResponseDto> success() {
    PatchOrderListResponseDto result = new PatchOrderListResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  // 실패 응답 (환불 실패)
  public static ResponseEntity<ResponseDto> refundFail() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.REFUND_FAIL, ResponseMessage.REFUND_FAIL);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  // 실패 응답 (잔액부족 없음)
  public static ResponseEntity<ResponseDto> insufficientBlance() {
    ResponseDto result = new ResponseDto(ResponseCode.INSUFFICIENT_BALANCE, ResponseMessage.INSUFFICIENT_BALANCE);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }

  // 실패 응답 (주문 없음)
  public static ResponseEntity<ResponseDto> noExistOrder() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ORDER, ResponseMessage.NOT_EXISTED_ORDER);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
  }

  // 실패 응답 (상태 없음)
  public static ResponseEntity<ResponseDto> noExistOrderStatus() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ORDER_STATUS, ResponseMessage.NOT_EXISTED_ORDER_STATUS);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
  }
}
