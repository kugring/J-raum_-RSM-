package com.kugring.back.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "`order_item_option`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemOptionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int orderItemOptionId;

  @ManyToOne
  @JoinColumn(name = "order_item_id")
  private OrderItemEntity orderItem;

  @ManyToOne
  @JoinColumn(name = "option_code")
  private OptionEntity option;

  @NotNull
  private int optionQuantity;


}

