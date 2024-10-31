package com.kugring.back.entity;

import com.kugring.back.dto.request.point.ApprovalPointChargeRequestDto;
import com.kugring.back.dto.request.point.PointDirectChargeRequestDto;
import com.kugring.back.dto.request.point.PostPointChargeRequestDto;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`point_charge`")
public class PointChargeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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



  // 포인트 충전을 요청하는 엔티티
  public PointChargeEntity(PostPointChargeRequestDto dto, UserEntity userEntity, int currentPoint) {
    this.user = userEntity;
    this.status = "미승인";
    this.currentPoint = currentPoint;
    this.createDate = LocalDateTime.now();
    this.chargePoint = dto.getChargePoint();
  }
  
  // 관리자가 충전에 대한 요청을 승인하는 엔티티
  public void approvalPointCharge(ApprovalPointChargeRequestDto dto) {
    this.status = "승인";
    this.approvalDate = LocalDateTime.now();
    this.managerId = dto.getManagerId();
  }

  // 매니저가 직접 충전하는 경우
  public PointChargeEntity(PointDirectChargeRequestDto dto, UserEntity userEntity, int currentPoint) {
    this.user = userEntity;
    this.status = "승인";
    this.createDate = LocalDateTime.now();
    this.approvalDate = LocalDateTime.now();
    this.currentPoint = currentPoint;
    this.managerId = dto.getManagerId();
    this.chargePoint = dto.getChargePoint();
  }


}
