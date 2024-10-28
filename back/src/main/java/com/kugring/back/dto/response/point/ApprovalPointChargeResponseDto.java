package com.kugring.back.dto.response.point;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kugring.back.common.ResponseCode;
import com.kugring.back.common.ResponseMessage;
import com.kugring.back.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class ApprovalPointChargeResponseDto extends ResponseDto {

  private ApprovalPointChargeResponseDto() {
    super();
  }

  public static ResponseEntity<ApprovalPointChargeResponseDto> success() {
    ApprovalPointChargeResponseDto responseBody = new ApprovalPointChargeResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> alreadyPointCharge() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.ALREADY_POINT_CHARGE, ResponseMessage.ALREADY_POINT_CHARGE);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> pointChargeFail() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.POINT_CHARGE_FAIL, ResponseMessage.POINT_CHARGE_FAIL);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noExistManager() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_MANAGER, ResponseMessage.NOT_EXISTED_MANAGER);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
  }
}
