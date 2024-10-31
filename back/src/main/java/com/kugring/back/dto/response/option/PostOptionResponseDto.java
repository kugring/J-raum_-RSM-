package com.kugring.back.dto.response.option;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kugring.back.common.ResponseCode;
import com.kugring.back.common.ResponseMessage;
import com.kugring.back.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class PostOptionResponseDto extends ResponseDto {

    private PostOptionResponseDto() {
        super();
    }

    public static ResponseEntity<PostOptionResponseDto> success() {
        PostOptionResponseDto result = new PostOptionResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 실패 응답 (option Create Fail)
    public static ResponseEntity<ResponseDto> optionCreateFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.OPTION_CREATE_FAIL, ResponseMessage.OPTION_CREATE_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    // 실패 응답 (옵션 없음)
    public static ResponseEntity<ResponseDto> aleardyOption() {
        ResponseDto result = new ResponseDto(ResponseCode.ALREADY_OPTION, ResponseMessage.ALREADY_OPTION);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
