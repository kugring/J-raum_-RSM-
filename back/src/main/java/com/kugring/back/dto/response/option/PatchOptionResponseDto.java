package com.kugring.back.dto.response.option;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kugring.back.common.ResponseCode;
import com.kugring.back.common.ResponseMessage;
import com.kugring.back.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class PatchOptionResponseDto extends ResponseDto{

    private PatchOptionResponseDto() {
        super();
    }

    public static ResponseEntity<PatchOptionResponseDto> success() {
        PatchOptionResponseDto result = new PatchOptionResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 실패 응답 (option Patch Fail)
    public static ResponseEntity<ResponseDto> optionPatchFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.OPTION_PATCH_FAIL, ResponseMessage.OPTION_PATCH_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    // 실패 응답 (옵션 없음)
    public static ResponseEntity<ResponseDto> noExistOption() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_OPTION, ResponseMessage.NOT_EXISTED_OPTION);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
