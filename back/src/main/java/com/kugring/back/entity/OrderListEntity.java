package com.kugring.back.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.kugring.back.dto.request.order.PostOrderListRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`order_list`")
public class OrderListEntity {


  @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int orderListId;

  private String orderStatus;
  private String payMethod;
  private LocalDateTime createOrderDate;
  private LocalDateTime completeOrderDate;

  @OneToMany(mappedBy = "orderList", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<OrderItemEntity> orderItems;

  @ManyToOne  @JoinColumn(name = "user_id")
  private UserEntity user;


  public OrderListEntity(PostOrderListRequestDto dto, UserEntity userEntity) {
    this.user = userEntity;
    this.payMethod = dto.getPayMethod();
    this.createOrderDate = LocalDateTime.now();
    this.orderStatus = "대기";
  }

}

