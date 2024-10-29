package com.kugring.back.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`menu`")
public class MenuEntity {

  @Id
  private int menuId;
  private String menuName;
  private String category;
  private int menuPrice;
  private String temperature;
  private int espressoShot;
  private String image;

  @OneToMany(mappedBy="menu", cascade= CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<OrderItemEntity> orderItem;

  @OneToMany(mappedBy="menu", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<MenuOptionListEntity> menuOptionListes;

}

