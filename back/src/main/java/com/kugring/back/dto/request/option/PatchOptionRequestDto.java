package com.kugring.back.dto.request.option;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchOptionRequestDto {
    
  private int status;
  private int sequence;
  private String category;

  @Min(0)
  private int optionPrice;
  @NotBlank
  private String optionDetail;
}
