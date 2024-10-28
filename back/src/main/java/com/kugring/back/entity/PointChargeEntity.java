package com.kugring.back.entity;

import com.kugring.back.dto.request.point.ApprovalPointChargeRequestDto;
import com.kugring.back.dto.request.point.PointDirectChargeRequestDto;
import com.kugring.back.dto.request.point.PostPointChargeRequestDto;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`point_charge`")
public class PointChargeEntity {

  @Id
  private int pointChargeId;
  private int managerId;

  @NotNull
  @Min(0)
  private int chargePoint;

  @NotNull
  @Min(0)
  private int currentPoint;

  private String status;
  private LocalDateTime createDate;
  private LocalDateTime approvalDate;


  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;



  // 임플리먼트에서 데이터를 save할때 일부 데이터를 받아와서 세이브를 진행
  public PointChargeEntity(PostPointChargeRequestDto dto, int currentPoint) {

    this.status = "미승인";
    this.createDate = LocalDateTime.now();
    this.currentPoint = currentPoint;
    this.chargePoint = dto.getChargePoint();
  }

  // 매니저가 직접 충전하는 경우
  public PointChargeEntity(PointDirectChargeRequestDto dto, int currentPoint) {

    this.status = "승인";
    this.createDate = LocalDateTime.now();
    this.approvalDate = LocalDateTime.now();
    this.currentPoint = currentPoint;
    this.managerId = dto.getManagerId();
    this.chargePoint = dto.getChargePoint();
  }

  public void approvalPointCharge(ApprovalPointChargeRequestDto dto) {


    this.status = "승인";
    this.approvalDate = LocalDateTime.now();
    this.managerId = dto.getManagerId();
  }

}
