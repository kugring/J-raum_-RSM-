package com.kugring.back.dto.response.menu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kugring.back.dto.object.MenuListItem;
import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.entity.MenuEntity;

import java.util.List;

import lombok.Getter;

// 메뉴 정보 요청에 대해 서버가 해줄 수 있는 반응은?
// 성공, exception: 데이터베이스 에러
@Getter
public class GetMenuListResponseDto extends ResponseDto {

  private List<MenuListItem> menuList;

  private GetMenuListResponseDto(List<MenuEntity> menuEntities) {
    super();
    this.menuList = MenuListItem.getList(menuEntities);
  }

  public static ResponseEntity<GetMenuListResponseDto> success(List<MenuEntity> menuEntities) {
    GetMenuListResponseDto result = new GetMenuListResponseDto(menuEntities);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
