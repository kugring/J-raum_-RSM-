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
public class OrderListObject {

  private int orderListId;
  private String userId;
  private String orderStatus;
  private String payMethod;
  private LocalDateTime createOrderDate;
  private LocalDateTime completeOrderDate;
  private List<OrderItemObject> orderItems;


  public OrderListObject(OrderListEntity orderListEntity) {
    this.userId = orderListEntity.getUser().getUserId();
    this.payMethod = orderListEntity.getPayMethod();
    this.orderListId = orderListEntity.getOrderListId();
    this.orderStatus = orderListEntity.getOrderStatus();
    this.createOrderDate = orderListEntity.getCreateOrderDate();
    this.completeOrderDate = orderListEntity.getCompleteOrderDate();
    this.orderItems = OrderItemObject.getList(orderListEntity.getOrderItems());
  }

  public static List<OrderListObject> getList(List<OrderListEntity> orderListEntities) {
    List<OrderListObject> list = new ArrayList<>();
    for (OrderListEntity orderListEntity : orderListEntities) {
      OrderListObject item = new OrderListObject(orderListEntity);
      list.add(item);
    }
    return list;
  }
}
