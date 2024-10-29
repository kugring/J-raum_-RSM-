package com.kugring.back.dto.request.order;

import java.util.List;

import com.kugring.back.dto.object.OrderItemObject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostOrderListRequestDto {

  @NotBlank
  private String userId;

  @NotBlank
  private String payMethod;

  @NotNull
  private List<OrderItemObject> orderItems;

}
