package com.kugring.back.entity;

import java.util.List;



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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`order_item`")
public class OrderItemEntity {


  @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int orderItemId;

  @NotNull
  private int orderItemQuantity;

  @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<OrderItemOptionEntity> orderItemOptions;
 
  @ManyToOne  @JoinColumn(name = "menu_id")
  private MenuEntity menu;

  @ManyToOne  @JoinColumn(name = "order_list_id")
  private OrderListEntity orderList;

}
