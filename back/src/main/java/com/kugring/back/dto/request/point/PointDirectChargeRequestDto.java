package com.kugring.back.dto.request.point;

import lombok.Getter;

@Getter
public class PointDirectChargeRequestDto {

  private String userId;
  private int managerId;
  private int chargePoint;

}
