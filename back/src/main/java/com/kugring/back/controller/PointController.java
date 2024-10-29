package com.kugring.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kugring.back.dto.request.point.ApprovalPointChargeRequestDto;
import com.kugring.back.dto.request.point.CancelPointChargeRequestDto;
import com.kugring.back.dto.request.point.DeletePointChargeRequestDto;
import com.kugring.back.dto.request.point.PointDirectChargeRequestDto;
import com.kugring.back.dto.response.point.ApprovalPointChargeResponseDto;
import com.kugring.back.dto.response.point.CancelPointChargeResponseDto;
import com.kugring.back.dto.response.point.DeletePointChargeResponseDto;
import com.kugring.back.dto.response.point.PointDirectChargeResponseDto;
import com.kugring.back.dto.request.point.PostPointChargeRequestDto;
import com.kugring.back.dto.response.point.PostPointChargeResponseDto;
import com.kugring.back.service.PointService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/point")
@RequiredArgsConstructor
public class PointController {

  private final PointService pointService;

  @PostMapping("/charge")
  public ResponseEntity<? super PostPointChargeResponseDto> postPointCharge(@RequestBody @Valid PostPointChargeRequestDto requestBody) {
    ResponseEntity<? super PostPointChargeResponseDto> response = pointService.postPointCharge(requestBody);
    return response;
  }

  @PostMapping("/direct-charge")
  public ResponseEntity<? super PointDirectChargeResponseDto> pointDirectCharge(@RequestBody @Valid PointDirectChargeRequestDto requestBody) {
    ResponseEntity<? super PointDirectChargeResponseDto> response = pointService.pointDirectCharge(requestBody);
    return response;
  }

  @PatchMapping("/approve")
  public ResponseEntity<? super ApprovalPointChargeResponseDto> approvePointCharge(@RequestBody @Valid ApprovalPointChargeRequestDto requestBody) {
    ResponseEntity<? super ApprovalPointChargeResponseDto> response = pointService.approvePointCharge(requestBody);
    return response;
  }

  @PatchMapping("/cancel")
  public ResponseEntity<? super CancelPointChargeResponseDto> cancelPointCharge(@RequestBody @Valid CancelPointChargeRequestDto requestBody) {
    ResponseEntity<? super CancelPointChargeResponseDto> response = pointService.cancelPointCharge(requestBody);
    return response;
  }

  @DeleteMapping("")
  public ResponseEntity<? super DeletePointChargeResponseDto> deletePointCharge(@RequestBody @Valid DeletePointChargeRequestDto requestBody) {
    ResponseEntity<? super DeletePointChargeResponseDto> response = pointService.deletePointCharge(requestBody);
    return response;
  }
}
