package com.kugring.back.service;

import org.springframework.http.ResponseEntity;
import com.kugring.back.dto.request.option.PatchOptionRequestDto;
import com.kugring.back.dto.request.option.PostOptionRequestDto;
import com.kugring.back.dto.response.option.GetOptionListResponseDto;
import com.kugring.back.dto.response.option.PatchOptionResponseDto;
import com.kugring.back.dto.response.option.PostOptionResponseDto;

public interface OptionService {
    
  ResponseEntity<? super GetOptionListResponseDto> getOptionList();
  
  ResponseEntity<? super PostOptionResponseDto> postOption(PostOptionRequestDto dto);
  ResponseEntity<? super PatchOptionResponseDto> patchOption(int optionId, PatchOptionRequestDto dto);

}
