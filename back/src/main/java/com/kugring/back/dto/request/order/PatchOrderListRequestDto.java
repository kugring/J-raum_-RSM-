package com.kugring.back.dto.request.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchOrderListRequestDto {

  private String orderStatus;
}
