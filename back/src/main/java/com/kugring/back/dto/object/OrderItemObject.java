package com.kugring.back.dto.object;

import java.util.ArrayList;
import java.util.List;

import com.kugring.back.entity.OrderItemEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemObject {

  private int menuId;
  private int menuPrice;
  private int orderItemId;
  private int espressoShot;
  private int orderItemQuantity;

  private String image;
  private String menuName;
  private String category;
  private String temperature;
  private List<OrderItemOptionObject> orderItemOptions;


  public OrderItemObject(OrderItemEntity orderItemEntity) {
    this.image = orderItemEntity.getMenu().getImage();
    this.menuId = orderItemEntity.getMenu().getMenuId();
    this.menuName = orderItemEntity.getMenu().getMenuName();
    this.category = orderItemEntity.getMenu().getCategory();
    this.menuPrice = orderItemEntity.getMenu().getMenuPrice();
    this.temperature = orderItemEntity.getMenu().getTemperature();
    this.espressoShot = orderItemEntity.getMenu().getEspressoShot();
    this.orderItemQuantity = orderItemEntity.getOrderItemQuantity();
    this.orderItemOptions = OrderItemOptionObject.getList(orderItemEntity.getOrderItemOptions());
  }

  public static List<OrderItemObject> getList(List<OrderItemEntity> orderItemEntities) {

    List<OrderItemObject> list = new ArrayList<>();
    for (OrderItemEntity orderItemEntity : orderItemEntities) {
      OrderItemObject item = new OrderItemObject(orderItemEntity);
      list.add(item);
    }
    return list;
  }
}
