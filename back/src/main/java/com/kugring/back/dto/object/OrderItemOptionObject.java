package com.kugring.back.dto.object;

import java.util.ArrayList;
import java.util.List;

import com.kugring.back.entity.OrderItemOptionEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemOptionObject {

  @NotBlank
  private String optionCode;

  @NotNull
  private int optionQuantity;


  // public OrderItemOption(OrderItemOptionEntity orderItemOptionEntity) {
  //   this.optionCode = orderItemOptionEntity.getOptionCode();
  //   this.optionQuantity = orderItemOptionEntity.getOptionQuantity();
  // }


  // public static List<OrderItemOption> getList(List<OrderItemOptionEntity> orderItemOptionEntities) {

  //   List<OrderItemOption> list = new ArrayList<>();
  //   for (OrderItemOptionEntity orderItemOptionEntity : orderItemOptionEntities) {
  //     OrderItemOption item = new OrderItemOption(orderItemOptionEntity);
  //     list.add(item);
  //   }
  //   return list;
  // }
}
