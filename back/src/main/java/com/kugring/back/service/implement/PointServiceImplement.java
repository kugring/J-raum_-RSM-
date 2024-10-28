package com.kugring.back.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kugring.back.dto.request.point.ApprovalPointChargeRequestDto;
import com.kugring.back.dto.request.point.CancelPointChargeRequestDto;
import com.kugring.back.dto.request.point.PointDirectChargeRequestDto;
import com.kugring.back.dto.response.point.ApprovalPointChargeResponseDto;
import com.kugring.back.dto.response.point.CancelPointChargeResponse;
import com.kugring.back.dto.response.point.PointDirectChargeResponseDto;
import com.kugring.back.dto.request.point.PostPointChargeRequestDto;
import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.dto.response.point.PostPointChargeResponseDto;
import com.kugring.back.entity.PointChargeEntity;
import com.kugring.back.entity.UserEntity;
import com.kugring.back.repository.ManagerRepository;
import com.kugring.back.repository.PointChargeRepositoy;
import com.kugring.back.repository.UserRepository;
import com.kugring.back.service.PointService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointServiceImplement implements PointService {

  private final UserRepository userRepository;
  private final PointChargeRepositoy pointChargeRepositoy;
  private final ManagerRepository managerRepository;

  @Override
  public ResponseEntity<? super PostPointChargeResponseDto> postPointCharge(PostPointChargeRequestDto dto) {
    try {

      String userId = dto.getUserId();
      int chargePoint = dto.getChargePoint();
      int currentPoint = userRepository.findByUserId(userId).getPoint();

      boolean existedUserId = userRepository.existsByUserId(userId);
      if (!existedUserId)
        return PostPointChargeResponseDto.noExistUser();
      if (chargePoint < 0)
        return PostPointChargeResponseDto.pointChargeFail();
      if (currentPoint < 0)
        return PostPointChargeResponseDto.pointChargeFail();

      PointChargeEntity pointChargeEntity = new PointChargeEntity(dto, currentPoint);
      pointChargeRepositoy.save(pointChargeEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PostPointChargeResponseDto.success();
  }

  @Override
  public ResponseEntity<? super PointDirectChargeResponseDto> pointDirectCharge(PointDirectChargeRequestDto dto) {
    try {

      // 예외처리를 위한 데이터 추출
      String userId = dto.getUserId();
      int managerId = dto.getManagerId();
      int chargePoint = dto.getChargePoint();
      boolean existedUserId = userRepository.existsByUserId(userId);
      int currentPoint = userRepository.findByUserId(userId).getPoint();

      // 예외처리
      if (!existedUserId)
        return PostPointChargeResponseDto.noExistUser();
      if (chargePoint < 0)
        return PostPointChargeResponseDto.pointChargeFail();
      if (currentPoint < 0)
        return PostPointChargeResponseDto.pointChargeFail();
      boolean existedManagerId = managerRepository.existsByManagerId(managerId);
      if (!existedManagerId)
        return ApprovalPointChargeResponseDto.noExistManager();

      // 엔티티 생성 후 저장
      PointChargeEntity pointChargeEntity = new PointChargeEntity(dto, currentPoint);
      pointChargeRepositoy.save(pointChargeEntity);

      // 찾은 userID로 유저데이터 조회
      UserEntity userEntity = userRepository.findByUserId(userId);

      // 기존 포인트에 충전금액을 더 해준다.
      userEntity.pointCharge(chargePoint);
      userRepository.save(userEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PointDirectChargeResponseDto.success();
  }

  @Override
  public ResponseEntity<? super ApprovalPointChargeResponseDto> approvePointCharge(ApprovalPointChargeRequestDto dto) {

    try {

      // 매니저ID, 포인트충전ID를 변수에 담는다.
      int managerId = dto.getManagerId();
      int pointChargeId = dto.getPointChargeId();

      // 매니저ID, 포인트충전ID이 비어있는 경우 예외처리.
      boolean existedManagerId = managerRepository.existsByManagerId(managerId);
      boolean existsByPointChargeIdAndStatus = pointChargeRepositoy.existsByPointChargeIdAndStatus(pointChargeId, "미승인");
      boolean existedPointChargeId = pointChargeRepositoy.existsByPointChargeId(pointChargeId);
      if (!existedManagerId)
        return ApprovalPointChargeResponseDto.noExistManager();
      if (!existsByPointChargeIdAndStatus)
        return ApprovalPointChargeResponseDto.alreadyPointCharge();
      if (!existedPointChargeId)
        return ApprovalPointChargeResponseDto.pointChargeFail();

      // 포인트충전ID로 엔티티를 찾고 수정된 데이터를 바꿔준다.
      PointChargeEntity pointChargeEntity = pointChargeRepositoy.findByPointChargeId(pointChargeId);
      pointChargeEntity.approvalPointCharge(dto);
      pointChargeRepositoy.save(pointChargeEntity);

      // 포인트충전의 userId와 충전금액을 찾는다.
      // String userId = pointChargeEntity.getUserId();
      int chargePoint = pointChargeEntity.getChargePoint();

      // 찾은 userID로 유저데이터 조회
      // UserEntity userEntity = userRepository.findByUserId(userId);

      // // 기존 포인트에 충전금액을 더 해준다.
      // userEntity.pointCharge(chargePoint);
      // userRepository.save(userEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ApprovalPointChargeResponseDto.success();

  }

  @Override
  public ResponseEntity<? super CancelPointChargeResponse> cancelPointCharge(CancelPointChargeRequestDto dto) {

    try {

      // dto에서 포인트충전ID를 추출
      int pointChargeId = dto.getPointChargeId();


      // 포인트충전ID가 없는 경우, 이미 승인되어진 경우 예외처리
      boolean existedPointChargeId = pointChargeRepositoy.existsByPointChargeId(pointChargeId);
      boolean existsByPointChargeIdAndStatus = pointChargeRepositoy.existsByPointChargeIdAndStatus(pointChargeId, "승인");
      if (!existedPointChargeId)
        return CancelPointChargeResponse.CancelPointChargeFail();
      if (!existsByPointChargeIdAndStatus)
        return ApprovalPointChargeResponseDto.alreadyPointCharge();
      // 포인트충전ID로 검색후 해당 엔터티 삭제
      PointChargeEntity pointChargeEntity = pointChargeRepositoy.findByPointChargeId(pointChargeId);
      pointChargeRepositoy.delete(pointChargeEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return CancelPointChargeResponse.success();
  }

}
