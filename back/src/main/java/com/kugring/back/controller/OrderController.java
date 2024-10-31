package com.kugring.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kugring.back.dto.request.order.FilterOrderListRequestDto;
import com.kugring.back.dto.request.order.PatchOrderListRequestDto;
import com.kugring.back.dto.request.order.PostOrderListRequestDto;
import com.kugring.back.dto.request.order.PutOrderListRequestDto;
import com.kugring.back.dto.response.order.FilterOrderListResponseDto;
import com.kugring.back.dto.response.order.PatchOrderListResponseDto;
import com.kugring.back.dto.response.order.PostOrderListResponseDto;
import com.kugring.back.dto.response.order.PutOrderListResponseDto;
import com.kugring.back.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;


  @PostMapping("/filter")
  public ResponseEntity<? super FilterOrderListResponseDto> getOrderList(@RequestBody @Valid FilterOrderListRequestDto reqeustBody) {
    ResponseEntity<? super FilterOrderListResponseDto> resposne = orderService.filterOrderList(reqeustBody);
    return resposne;
  }

  @PostMapping("")
  public ResponseEntity<? super PostOrderListResponseDto> postOrder(@RequestBody @Valid PostOrderListRequestDto reqeustBody) {
    ResponseEntity<? super PostOrderListResponseDto> resposne = orderService.postOrderList(reqeustBody);
    return resposne;
  }

  @PatchMapping("/{orderListId}")
  public ResponseEntity<? super PatchOrderListResponseDto> patchOrder(@RequestBody @Valid PatchOrderListRequestDto requestBody,
      @PathVariable("orderListId") Integer orderListId) {
    ResponseEntity<? super PatchOrderListResponseDto> resposne = orderService.patchOrderList(orderListId, requestBody);
    return resposne;
  }

  @PutMapping("/{orderListId}")
  public ResponseEntity<? super PutOrderListResponseDto> putOrderList(@RequestBody @Valid PutOrderListRequestDto requestBody,
      @PathVariable("orderListId") Integer orderListId) {
    ResponseEntity<? super PutOrderListResponseDto> resposne = orderService.putOrderList(orderListId, requestBody);
    return resposne;
  }
}
