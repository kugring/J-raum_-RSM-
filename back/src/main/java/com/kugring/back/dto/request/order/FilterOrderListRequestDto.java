// GetOrderListRequestDto.java
package com.kugring.back.dto.request.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FilterOrderListRequestDto {

  private String userId;
  private String orderStatus;
  private LocalDateTime startCreateDate;
  private LocalDateTime endCreateDate;
  private LocalDateTime startCompleteDate;
  private LocalDateTime endCompleteDate;
}
