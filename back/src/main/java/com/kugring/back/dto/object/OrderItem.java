package com.kugring.back.dto.object;

import java.util.ArrayList;
import java.util.List;

import com.kugring.back.entity.OrderItemEntity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

  private int orderItemId;

  private int menuId;

  @NotNull
  private int orderItemQuantity;


  private List<OrderItemOption> orderItemOptions;


  // public OrderItem(OrderItemEntity orderItemEntity) {
  //   this.menuId = orderItemEntity.getMenu().getMenuId();
  //   this.orderItemQuantity = orderItemEntity.getOrderItemQuantity();
  //   this.orderItemOptions = OrderItemOption.getList(orderItemEntity.getOrderItemOptions());
  // }

  // public static List<OrderItem> getList(List<OrderItemEntity> orderItemEntities) {

  //   List<OrderItem> list = new ArrayList<>();
  //   for (OrderItemEntity orderItemEntity : orderItemEntities) {
  //     OrderItem item = new OrderItem(orderItemEntity);
  //     list.add(item);
  //   }
  //   return list;
  // }
}
