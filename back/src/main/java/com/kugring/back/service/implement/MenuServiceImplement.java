package com.kugring.back.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.dto.response.menu.GetMenuListResponseDto;
import com.kugring.back.dto.response.option.GetOptionListResponseDto;
import com.kugring.back.entity.MenuEntity;
import com.kugring.back.entity.OptionEntity;
import com.kugring.back.repository.MenuRepository;
import com.kugring.back.repository.OptionRepository;
import com.kugring.back.service.MenuService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImplement implements MenuService {

  private final MenuRepository menuRepository;
  private final OptionRepository optionRepository;
  private List<MenuEntity> menuEntities;
  private List<OptionEntity> optionEntities;

  @Override
  public ResponseEntity<? super GetMenuListResponseDto> getMenuList() {

    try {
      // 모든 메뉴 정보를 담는다.
      menuEntities = menuRepository.findAll();
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetMenuListResponseDto.success(menuEntities);
  }

  @Override
  public ResponseEntity<? super GetOptionListResponseDto> getOptionList(int menuId) {
    try {

      // 메뉴ID를 가지고 해당 옵션들을 가져온다.
      optionEntities = optionRepository.getOptionList(menuId);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetOptionListResponseDto.success(optionEntities);
  }

};
