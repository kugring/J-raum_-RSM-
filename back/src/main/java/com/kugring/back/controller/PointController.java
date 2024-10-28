package com.kugring.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kugring.back.dto.request.point.ApprovalPointChargeRequestDto;
import com.kugring.back.dto.request.point.CancelPointChargeRequestDto;
import com.kugring.back.dto.request.point.PointDirectChargeRequestDto;
import com.kugring.back.dto.response.point.ApprovalPointChargeResponseDto;
import com.kugring.back.dto.response.point.CancelPointChargeResponse;
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

  @PostMapping("/approve")
  public ResponseEntity<? super ApprovalPointChargeResponseDto> approvePointCharge(@RequestBody @Valid ApprovalPointChargeRequestDto requestBody) {
    ResponseEntity<? super ApprovalPointChargeResponseDto> response = pointService.approvePointCharge(requestBody);
    return response;
  }

  @PostMapping("/cancel")
  public ResponseEntity<? super CancelPointChargeResponse> cancelPointCharge(@RequestBody @Valid CancelPointChargeRequestDto requestBody) {
    ResponseEntity<? super CancelPointChargeResponse> response = pointService.cancelPointCharge(requestBody);
    return response;
  }
}
