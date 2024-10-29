package com.kugring.back.dto.object;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kugring.back.entity.OrderListEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListItemObject {

  private int orderListId;
  private String userId;
  private String orderStatus;
  private String payMethod;
  private LocalDateTime createOrderDate;
  private LocalDateTime completeOrderDate;
  private List<OrderItemObject> orderItems;


  // public OrderListItem(OrderListEntity orderListEntity) {
  //   this.orderListId = orderListEntity.getOrderListId();
  //   this.userId = orderListEntity.getUserId();
  //   this.orderStatus = orderListEntity.getOrderStatus();
  //   this.payMethod = orderListEntity.getPayMethod();
  //   this.createOrderDate = orderListEntity.getCreateOrderDate();
  //   this.completeOrderDate = orderListEntity.getCompleteOrderDate();
  //   this.orderItems = OrderItem.getList(orderListEntity.getOrderItems());
  // }

  // public static List<OrderListItem> getList(List<OrderListEntity> orderListEntities) {
  //   List<OrderListItem> list = new ArrayList<>();
  //   for (OrderListEntity orderListEntity : orderListEntities) {
  //     OrderListItem item = new OrderListItem(orderListEntity);
  //     list.add(item);
  //   }
  //   return list;
  // }
}
