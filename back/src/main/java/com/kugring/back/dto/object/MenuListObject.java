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
public class MenuListObject {

  private int menuId;
  private String menuName;
  private String category;
  private int menuPrice;
  private String temperature;
  private int espressoShot;
  private String image;
  private List<OptionCodeObject> optionList;

  public MenuListObject(MenuEntity menuEntity) {
    this.menuId = menuEntity.getMenuId();
    this.menuName = menuEntity.getMenuName();
    this.category = menuEntity.getCategory();
    this.menuPrice = menuEntity.getMenuPrice();
    this.temperature = menuEntity.getTemperature();
    this.espressoShot = menuEntity.getEspressoShot();
    this.image = menuEntity.getImage();
    this.optionList = getOptionList(menuEntity.getMenuOptionListes());
  }

  // 메뉴 엔터티를 객체로 변환
  public static List<MenuListObject> getList(List<MenuEntity> menuEntities) {
    List<MenuListObject> list = new ArrayList<>();
    for (MenuEntity menuEntity : menuEntities) {
      MenuListObject menuListItem = new MenuListObject(menuEntity);
      list.add(menuListItem);
    }
    return list;
  }

  // 옵션 리스트로 변환
  public static List<OptionCodeObject> getOptionList(List<MenuOptionListEntity> menuOptionListes) {
    List<OptionCodeObject> list = new ArrayList<>();
    for (MenuOptionListEntity menuOptionListEntity : menuOptionListes) {
      OptionEntity optionEntity = menuOptionListEntity.getOption();
      list.add(new OptionCodeObject(optionEntity));
    }
    return list;
  }

}
