package com.kugring.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kugring.back.dto.request.menu.PatchMenuRequestDto;
import com.kugring.back.dto.request.menu.PostMenuRequestDto;
import com.kugring.back.dto.response.menu.GetMenuListResponseDto;
import com.kugring.back.dto.response.menu.PatchMenuResponseDto;
import com.kugring.back.dto.response.menu.PostMenuResponseDto;
import com.kugring.back.service.MenuService;
import jakarta.validation.Valid;
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

  @PostMapping("")
  public ResponseEntity<? super PostMenuResponseDto> getMenuList(@RequestBody @Valid PostMenuRequestDto requestBody) {
    ResponseEntity<? super PostMenuResponseDto> response = menuService.postMenu(requestBody);
    return response;
  }

  @PatchMapping("/{menuId}")
  public ResponseEntity<? super PatchMenuResponseDto> patchMenu(@RequestBody @Valid PatchMenuRequestDto requestBody, 
    @PathVariable("menuId") Integer menuId) {
    ResponseEntity<? super PatchMenuResponseDto> response = menuService.patchMenu(menuId, requestBody);
    return response;
  }

}
