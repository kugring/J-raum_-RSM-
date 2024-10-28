package com.kugring.back.service;

import org.springframework.http.ResponseEntity;
import com.kugring.back.dto.request.point.PostPointChargeRequestDto;
import com.kugring.back.dto.response.point.CancelPointChargeResponse;
import com.kugring.back.dto.request.point.CancelPointChargeRequestDto;
import com.kugring.back.dto.request.point.PointDirectChargeRequestDto;
import com.kugring.back.dto.response.point.PostPointChargeResponseDto;
import com.kugring.back.dto.response.point.PointDirectChargeResponseDto;
import com.kugring.back.dto.request.point.ApprovalPointChargeRequestDto;
import com.kugring.back.dto.response.point.ApprovalPointChargeResponseDto;

public interface PointService {

  ResponseEntity<? super PostPointChargeResponseDto> postPointCharge(PostPointChargeRequestDto dto);

  ResponseEntity<? super CancelPointChargeResponse> cancelPointCharge(CancelPointChargeRequestDto dto);

  ResponseEntity<? super PointDirectChargeResponseDto> pointDirectCharge(PointDirectChargeRequestDto dto);

  ResponseEntity<? super ApprovalPointChargeResponseDto> approvePointCharge(ApprovalPointChargeRequestDto dto);



}
