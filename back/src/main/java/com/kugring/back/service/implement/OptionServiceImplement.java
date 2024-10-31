package com.kugring.back.service.implement;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.kugring.back.dto.request.option.PatchOptionRequestDto;
import com.kugring.back.dto.request.option.PostOptionRequestDto;
import com.kugring.back.dto.response.ResponseDto;
import com.kugring.back.dto.response.option.GetOptionListResponseDto;
import com.kugring.back.dto.response.option.PatchOptionResponseDto;
import com.kugring.back.dto.response.option.PostOptionResponseDto;
import com.kugring.back.entity.OptionEntity;
import com.kugring.back.repository.OptionRepository;
import com.kugring.back.service.OptionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionServiceImplement implements OptionService {

    private final OptionRepository optionRepository;

    @Override
    public ResponseEntity<? super GetOptionListResponseDto> getOptionList() {
        // 트라이 밖에 선언
        List<OptionEntity> optionEntities = new ArrayList<>();
        try {
            // 모든 옵션들을 불러온다.
            optionEntities = optionRepository.findAll();
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseDto.databaseError();
        }
        return GetOptionListResponseDto.success(optionEntities);
    }

    @Override
    public ResponseEntity<? super PostOptionResponseDto> postOption(PostOptionRequestDto dto) {
        try {
            // 이미 존재하는지 확인
            boolean existsOption = optionRepository.existsByOptionDetail(dto.getOptionDetail());
            // 이미 있는 경우 예외처리
            if(existsOption) return PostOptionResponseDto.aleardyOption();
            // 새로운 데이터 생성
            optionRepository.save(new OptionEntity(dto));
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseDto.databaseError();
        }
        return PostOptionResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchOptionResponseDto> patchOption(int optionId, PatchOptionRequestDto dto) {

        try {
            // 옵션을 조회함
            OptionEntity optionEntity = optionRepository.findByOptionId(optionId);
            // 옵션이 존재하지 않는경우 예외처리
            if(optionEntity == null) return PatchOptionResponseDto.noExistOption();
            // 데이터 수정
            optionEntity.patchOption(dto);

            // todo : 궁금한 점 -> 만일 pk인 optionCode를 수정하면 알아서 서브 테이블 값들도 다 변경되는가?

            // 수정된 옵션 저장
            optionRepository.save(optionEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseDto.databaseError();
        }
        return PatchOptionResponseDto.success();
    }

}