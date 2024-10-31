package com.kugring.back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.kugring.back.dto.object.MenuOptionListObject;
import com.kugring.back.dto.request.menu.PatchMenuRequestDto;
import com.kugring.back.dto.request.menu.PostMenuRequestDto;
import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.dto.response.menu.GetMenuListResponseDto;
import com.kugring.back.dto.response.menu.PatchMenuResponseDto;
import com.kugring.back.dto.response.menu.PostMenuResponseDto;
import com.kugring.back.dto.response.option.GetOptionListResponseDto;
import com.kugring.back.entity.MenuEntity;
import com.kugring.back.entity.MenuOptionListEntity;
import com.kugring.back.entity.OptionEntity;
import com.kugring.back.repository.MenuRepository;
import com.kugring.back.repository.OptionRepository;
import com.kugring.back.service.MenuService;
import jakarta.transaction.Transactional;
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

  @Override @Transactional
  public ResponseEntity<? super PostMenuResponseDto> postMenu(PostMenuRequestDto dto) {
    try {
      // 그대로 정보를 담고 저장
      MenuEntity menuEntity = new MenuEntity(dto);

      // 메뉴 옵션 리스트 생성
      List<MenuOptionListEntity> menuOptionListes = new ArrayList<>();

      // 반복문으로 메뉴옵션엔티티를 생성함
      for(MenuOptionListObject option : dto.getOptions()){

        // 옵션엔터티 조회
        OptionEntity optionEntity = optionRepository.findByOptionId(option.getOptionId());

        // 옵션 코드가 아닌 경우 예외처리
        if (optionEntity == null) PostMenuResponseDto.menuCreateFail();

        // 메뉴옵션엔티티 생성
        MenuOptionListEntity menuOptionListEntity = new MenuOptionListEntity();
        
        // 메뉴엔티티와 메뉴를 연동
        menuOptionListEntity.setMenu(menuEntity);

        // 메뉴엔티티와 옵션을 연동
        menuOptionListEntity.setOption(optionEntity);

        // 생성된 메뉴옵션엔티티를 추가
        menuOptionListes.add(menuOptionListEntity);
      }

      // 메뉴옵션 정보를 저장
      menuEntity.setMenuOptionListes(menuOptionListes);

      // 메뉴 정보를 저장
      menuRepository.save(menuEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return PostMenuResponseDto.success();
  }

  @Override
  public ResponseEntity<? super PatchMenuResponseDto> patchMenu(int menuId, PatchMenuRequestDto dto) {
    try {

      // 메뉴 조회하기
      MenuEntity menuEntity = menuRepository.findByMenuId(menuId);

      // 데이터 수정하기
      menuEntity.patchMenu(dto);

      // 메뉴 옵션 리스트 가져오기
      List<MenuOptionListEntity> menuOptionListes = menuEntity.getMenuOptionListes();

      // 옵션 초기화하기
      menuOptionListes.clear();

      // 반복문으로 메뉴옵션엔티티를 생성함
      for(MenuOptionListObject option : dto.getOptions()){

        // 옵션엔터티 조회
        OptionEntity optionEntity = optionRepository.findByOptionId(option.getOptionId());

        // 옵션 코드가 아닌 경우 예외처리
        if (optionEntity == null) PostMenuResponseDto.menuCreateFail();

        // 메뉴옵션엔티티 생성
        MenuOptionListEntity menuOptionListEntity = new MenuOptionListEntity();
        
        // 메뉴엔티티와 메뉴를 연동
        menuOptionListEntity.setMenu(menuEntity);

        // 메뉴엔티티와 옵션을 연동
        menuOptionListEntity.setOption(optionEntity);

        // 생성된 메뉴옵션엔티티를 추가
        menuOptionListes.add(menuOptionListEntity);
      }

      // 메뉴옵션 정보를 저장
      menuEntity.setMenuOptionListes(menuOptionListes);

      // 메뉴 정보를 저장
      menuRepository.save(menuEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return PatchMenuResponseDto.success();
  }

};
