package com.kugring.back.dto.request.order;

import java.util.List;

import com.kugring.back.dto.object.OrderItemObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PutOrderListRequestDto {

  private List<OrderItemObject> orderItems;
}
