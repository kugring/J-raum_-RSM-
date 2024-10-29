package com.kugring.back.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kugring.back.common.CertificationNumber;
import com.kugring.back.dto.request.auth.CheckCertificationRequestDto;
import com.kugring.back.dto.request.auth.EmailCertificationRequestDto;
import com.kugring.back.dto.request.auth.IdCheckRequestDto;
import com.kugring.back.dto.request.auth.PinCheckRequestDto;
import com.kugring.back.dto.request.auth.SignInRequestDto;
import com.kugring.back.dto.request.auth.SignUpRequestDto;
import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.dto.response.auth.CheckCertificationResponseDto;
import com.kugring.back.dto.response.auth.EmailCertificationResponseDto;
import com.kugring.back.dto.response.auth.IdCheckResponseDto;
import com.kugring.back.dto.response.auth.PinCheckResponseDto;
import com.kugring.back.dto.response.auth.SignInResponseDto;
import com.kugring.back.dto.response.auth.SignUpResponseDto;
import com.kugring.back.entity.CertificationEntity;
import com.kugring.back.entity.UserEntity;
import com.kugring.back.provider.EmailProvider;
import com.kugring.back.provider.JwtProvider;
import com.kugring.back.repository.CertificationRepository;
import com.kugring.back.repository.UserRepository;
import com.kugring.back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

  private final UserRepository userRepository;
  private final CertificationRepository certificationRepository;

  private final JwtProvider jwtProvider;
  private final EmailProvider emailProvider;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 스프링 시큐리티 cryto에서 제공하는
                                                                         // passwordEncoder이다!

  @Override
  public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {

    try {


      String userId = dto.getId();
      boolean isExisted = userRepository.existsByUserId(userId);
      if (isExisted)
        return IdCheckResponseDto.duplicateId();

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return IdCheckResponseDto.success();

  }

  @Override
  public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
    try {

      String userId = dto.getId();
      String email = dto.getEmail();

      boolean isexistId = userRepository.existsByUserId(userId);
      if (isexistId)
        return EmailCertificationResponseDto.duplicateId();

      String certificationNumber = CertificationNumber.getCertificationNumber();

      boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
      if (!isSuccessed)
        return EmailCertificationResponseDto.mailSendFail();

      CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
      certificationRepository.save(certificationEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return EmailCertificationResponseDto.success();
  }

  @Override
  public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
    try {

      String userId = dto.getId();
      String email = dto.getEmail();
      String certifiactionNumber = dto.getCertificationNumber();

      CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
      if (certificationEntity == null)
        return CheckCertificationResponseDto.certificationFail();

      // 요청 Dto에서 받아온 데이터와 DB에 있는 userId값으로 가져온 엔터티로 해당 데이터들을 비교한다.
      boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certifiactionNumber);

      if (!isMatched)
        return CheckCertificationResponseDto.certificationFail();

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return CheckCertificationResponseDto.success();
  }

  @Override
  public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
    try {
      String userId = dto.getId();
      boolean isexistId = userRepository.existsByUserId(userId);
      if (isexistId)
        return SignUpResponseDto.duplicateId();

      String email = dto.getEmail();
      String certificationNumber = dto.getCertificationNumber();
      CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);

      boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);
      if (!isMatched)
        return SignUpResponseDto.certificationFail();

      String password = dto.getPassword();
      String encodedPassword = passwordEncoder.encode(password); // 패스워드를 넣어서 해당 데이터를 인코딩 시킨다.
      dto.setPassword(encodedPassword);

      UserEntity userEntity = new UserEntity(dto);
      userRepository.save(userEntity);

      certificationRepository.deleteByUserId(userId);


    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return SignUpResponseDto.success();
  }

  @Override
  public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

    String token = null;

    try {

      String userId = dto.getId();
      UserEntity userEntity = userRepository.findByUserId(userId);
      if (userEntity == null)
        return SignInResponseDto.signInFail();

      String password = dto.getPassword();
      String encodedPassword = userEntity.getPassword();
      boolean isMatched = passwordEncoder.matches(password, encodedPassword);
      if (!isMatched)
        return SignInResponseDto.signInFail();

      token = jwtProvider.create(userId);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return SignInResponseDto.success(token);
  }

  @Override
  public ResponseEntity<? super PinCheckResponseDto> pinCheck(PinCheckRequestDto dto) {

    // 회원 정보를 담을 객체 선언
    UserEntity userEntity;
    try {
      // Dto에서 핀번호 가져옴
      String pin = dto.getPin();
      // pin으로 데이터 조회
      userEntity = userRepository.findByPin(pin);
      // 정보가 없다면 예외처리
      if (userEntity == null) return PinCheckResponseDto.pinFail();

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PinCheckResponseDto.success(userEntity);

  }

}
