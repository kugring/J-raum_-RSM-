package com.kugring.back.dto.response.point;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kugring.back.common.ResponseCode;
import com.kugring.back.common.ResponseMessage;
import com.kugring.back.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class PostPointChargeResponseDto extends ResponseDto {

  private PostPointChargeResponseDto() {
    super();
  }

  public static ResponseEntity<PostPointChargeResponseDto> success() {
    PostPointChargeResponseDto responseBody = new PostPointChargeResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> pointChargeFail() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.POINT_CHARGE_FAIL, ResponseMessage.POINT_CHARGE_FAIL);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noExistUser() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
  }
}

