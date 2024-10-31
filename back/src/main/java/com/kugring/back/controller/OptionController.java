package com.kugring.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kugring.back.dto.request.option.PatchOptionRequestDto;
import com.kugring.back.dto.request.option.PostOptionRequestDto;
import com.kugring.back.dto.response.option.GetOptionListResponseDto;
import com.kugring.back.dto.response.option.PatchOptionResponseDto;
import com.kugring.back.dto.response.option.PostOptionResponseDto;
import com.kugring.back.service.OptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/option")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @GetMapping("/list")
    public ResponseEntity<? super GetOptionListResponseDto> getOptionList() {
        ResponseEntity<? super GetOptionListResponseDto> respone = optionService.getOptionList();
        return respone;
    }

    @PostMapping("")
    public ResponseEntity<? super PostOptionResponseDto> postOption(@RequestBody @Valid PostOptionRequestDto requestBody) {
        ResponseEntity<? super PostOptionResponseDto> respone = optionService.postOption(requestBody);
        return respone;
    }

    @PatchMapping("/{optionId}")
    public ResponseEntity<? super PatchOptionResponseDto> patchOption(@RequestBody @Valid PatchOptionRequestDto requestBody,
            @PathVariable("optionId") int optionId) {
        ResponseEntity<? super PatchOptionResponseDto> respone = optionService.patchOption(optionId, requestBody);
        return respone;
    }
}
