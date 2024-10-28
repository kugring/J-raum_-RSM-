package com.kugring.back.dto.object;

import com.kugring.back.entity.MenuEntity;

import java.util.List;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuListItem {

  private int menuId;
  private String menuName;
  private String category;
  private int menuPrice;
  private String temperature;
  private int espressoShot;
  private String image;
  private OptionListItem optionListItem;

  public MenuListItem(MenuEntity menuEntity) {
    this.menuId = menuEntity.getMenuId();
    this.menuName = menuEntity.getMenuName();
    this.category = menuEntity.getCategory();
    this.menuPrice = menuEntity.getMenuPrice();
    this.temperature = menuEntity.getTemperature();
    this.espressoShot = menuEntity.getEspressoShot();
    this.image = menuEntity.getImage();
  }

  public static List<MenuListItem> getList(List<MenuEntity> menuEntities) {
    List<MenuListItem> list = new ArrayList<>();
    for (MenuEntity menuEntity : menuEntities) {
      MenuListItem menuListItem = new MenuListItem(menuEntity);
      list.add(menuListItem);
    }
    return list;
  }

}
