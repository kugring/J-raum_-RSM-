package com.kugring.back.dto.object;

import java.util.ArrayList;
import java.util.List;
import com.kugring.back.entity.OrderItemOptionEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemOptionObject {


  @NotBlank
  private int optionId;
  @NotBlank
  private String optionDetail;
  @NotBlank
  private int optionPrice;
  @NotNull
  private int optionQuantity;
  @NotBlank
  private int orderItemOptionId;

  public OrderItemOptionObject(OrderItemOptionEntity orderItemOptionEntity) {
    this.optionId = orderItemOptionEntity.getOption().getOptionId();
    this.optionPrice = orderItemOptionEntity.getOption().getOptionPrice();
    this.optionDetail = orderItemOptionEntity.getOption().getOptionDetail();
    this.optionQuantity = orderItemOptionEntity.getOptionQuantity();
    this.orderItemOptionId = orderItemOptionEntity.getOrderItemOptionId();
  }


  public static List<OrderItemOptionObject> getList(List<OrderItemOptionEntity> orderItemOptionEntities) {

    List<OrderItemOptionObject> list = new ArrayList<>();
    for (OrderItemOptionEntity orderItemOptionEntity : orderItemOptionEntities) {
      OrderItemOptionObject item = new OrderItemOptionObject(orderItemOptionEntity);
      list.add(item);
    }
    return list;
  }
}
