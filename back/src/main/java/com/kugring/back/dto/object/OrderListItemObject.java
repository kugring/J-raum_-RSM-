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


  public OrderListItemObject(OrderListEntity orderListEntity) {
    this.orderListId = orderListEntity.getOrderListId();
    this.userId = orderListEntity.getUser().getUserId();
    this.orderStatus = orderListEntity.getOrderStatus();
    this.payMethod = orderListEntity.getPayMethod();
    this.createOrderDate = orderListEntity.getCreateOrderDate();
    this.completeOrderDate = orderListEntity.getCompleteOrderDate();
    this.orderItems = OrderItemObject.getList(orderListEntity.getOrderItems());
  }

  public static List<OrderListItemObject> getList(List<OrderListEntity> orderListEntities) {
    List<OrderListItemObject> list = new ArrayList<>();
    for (OrderListEntity orderListEntity : orderListEntities) {
      OrderListItemObject item = new OrderListItemObject(orderListEntity);
      list.add(item);
    }
    return list;
  }
}
