package com.kugring.back.service;

import org.springframework.http.ResponseEntity;
import com.kugring.back.dto.request.menu.PatchMenuRequestDto;
import com.kugring.back.dto.request.menu.PostMenuRequestDto;
import com.kugring.back.dto.response.menu.GetMenuListResponseDto;
import com.kugring.back.dto.response.menu.PatchMenuResponseDto;
import com.kugring.back.dto.response.menu.PostMenuResponseDto;
import com.kugring.back.dto.response.option.GetOptionListResponseDto;

public interface MenuService {

  ResponseEntity<? super GetMenuListResponseDto> getMenuList();

  ResponseEntity<? super GetOptionListResponseDto> getOptionList(int menuId);

  ResponseEntity<? super PostMenuResponseDto> postMenu(PostMenuRequestDto dto);

  ResponseEntity<? super PatchMenuResponseDto> patchMenu(int menuId, PatchMenuRequestDto dto);

}
