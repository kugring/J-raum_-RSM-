package com.kugring.back.service;

import org.springframework.http.ResponseEntity;

import com.kugring.back.dto.response.menu.GetMenuListResponseDto;
import com.kugring.back.dto.response.option.GetOptionListResponseDto;

public interface MenuService {

  ResponseEntity<? super GetMenuListResponseDto> getMenuList();

  ResponseEntity<? super GetOptionListResponseDto> getOptionList(int menuId);

}
