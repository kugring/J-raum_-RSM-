package com.kugring.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kugring.back.dto.response.menu.GetMenuListResponseDto;
import com.kugring.back.dto.response.option.GetOptionListResponseDto;
import com.kugring.back.service.MenuService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;

  @GetMapping("/list")
  public ResponseEntity<? super GetMenuListResponseDto> getMenuList() {
    ResponseEntity<? super GetMenuListResponseDto> response = menuService.getMenuList();
    return response;
  }

  @GetMapping("/{menuId}/option")
  public ResponseEntity<? super GetOptionListResponseDto> getOptionList(@PathVariable("menuId") int menuId) {
    ResponseEntity<? super GetOptionListResponseDto> respone = menuService.getOptionList(menuId);
    return respone;
  }
}
