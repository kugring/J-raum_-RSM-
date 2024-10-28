package com.kugring.back.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kugring.back.common.ResponseCode;
import com.kugring.back.common.ResponseMessage;
import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.entity.UserEntity;

import lombok.Getter;

@Getter
public class PinCheckResponseDto extends ResponseDto {

  private String userId;
  private String userName;
  private String nickname;
  private int point;
  private String office;
  private String profileImage;


  private PinCheckResponseDto(UserEntity userEntity) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.userId = userEntity.getUserId();
    this.userName = userEntity.getUserName();
    this.nickname = userEntity.getNickname();
    this.point = userEntity.getPoint();
    this.office = userEntity.getOffice();
    this.profileImage = userEntity.getProfileImage();
  }

  public static ResponseEntity<PinCheckResponseDto> success(UserEntity userEntity) {
    PinCheckResponseDto responseBody = new PinCheckResponseDto(userEntity);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }


  public static ResponseEntity<ResponseDto> pinFail() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.PIN_CHECK_FAIL, ResponseMessage.PIN_CHECK_FAIL);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }
}
