package com.kugring.back.dto.object;

import com.kugring.back.entity.MenuEntity;
import com.kugring.back.entity.MenuOptionListEntity;
import com.kugring.back.entity.OptionEntity;
import java.util.List;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuObject {

  private int menuId;
  private int status;
  private int sequence;
  private int menuPrice;
  private int espressoShot;
  private String image;
  private String menuName;
  private String category;
  private String temperature;
  private List<OptionObject> optionList;

  public MenuObject(MenuEntity menuEntity) {
    this.image = menuEntity.getImage();
    this.menuId = menuEntity.getMenuId();
    this.menuName = menuEntity.getMenuName();
    this.category = menuEntity.getCategory();
    this.menuPrice = menuEntity.getMenuPrice();
    this.temperature = menuEntity.getTemperature();
    this.espressoShot = menuEntity.getEspressoShot();
    this.optionList = getOptionList(menuEntity.getMenuOptionListes());
  }

  // 메뉴 엔터티를 객체로 변환
  public static List<MenuObject> getList(List<MenuEntity> menuEntities) {
    List<MenuObject> list = new ArrayList<>();
    for (MenuEntity menuEntity : menuEntities) {
      MenuObject menuListItem = new MenuObject(menuEntity);
      list.add(menuListItem);
    }
    return list;
  }

  // 옵션 리스트로 변환
  public static List<OptionObject> getOptionList(List<MenuOptionListEntity> menuOptionListes) {
    List<OptionObject> list = new ArrayList<>();
    for (MenuOptionListEntity menuOptionListEntity : menuOptionListes) {
      OptionEntity optionEntity = menuOptionListEntity.getOption();
      list.add(new OptionObject(optionEntity));
    }
    return list;
  }

}
