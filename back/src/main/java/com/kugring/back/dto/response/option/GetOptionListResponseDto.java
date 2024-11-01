package com.kugring.back.dto.response.option;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kugring.back.dto.object.OptionObject;
import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.entity.OptionEntity;

import lombok.Getter;

@Getter
public class GetOptionListResponseDto extends ResponseDto {

  private List<OptionObject> optionList;

  private GetOptionListResponseDto(List<OptionEntity> optionEntities) {
    super();
    this.optionList = OptionObject.getList(optionEntities);
  }

  public static ResponseEntity<GetOptionListResponseDto> success(List<OptionEntity> optionEntities) {
    GetOptionListResponseDto result = new GetOptionListResponseDto(optionEntities);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
