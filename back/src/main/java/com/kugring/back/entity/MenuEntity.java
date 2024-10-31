package com.kugring.back.entity;

import java.util.ArrayList;
import java.util.List;
import com.kugring.back.dto.request.menu.PatchMenuRequestDto;
import com.kugring.back.dto.request.menu.PostMenuRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int menuId;
  private int status;
  private int sequence;
  private int menuPrice;
  private int espressoShot;

  private String image;
  private String category;
  private String menuName;
  private String temperature;


  @OneToMany(mappedBy="menu", cascade= CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<OrderItemEntity> orderItem;

  @OneToMany(mappedBy="menu", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<MenuOptionListEntity> menuOptionListes = new ArrayList<>();



  public MenuEntity(PostMenuRequestDto dto){
    this.image = dto.getImage();
    this.status = dto.getStatus();
    this.menuName = dto.getMenuName();
    this.category = dto.getCategory();
    this.sequence = dto.getSequence();
    this.menuPrice = dto.getMenuPrice();
    this.temperature = dto.getTemperature(); 
    this.espressoShot = dto.getEspressoShot();
  }

  public void patchMenu(PatchMenuRequestDto dto){
    this.image = dto.getImage();
    this.status = dto.getStatus();
    this.menuName = dto.getMenuName();
    this.category = dto.getCategory();
    this.sequence = dto.getSequence();
    this.menuPrice = dto.getMenuPrice();
    this.temperature = dto.getTemperature(); 
    this.espressoShot = dto.getEspressoShot();
  }

}

