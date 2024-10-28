package com.kugring.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`option_code`")
public class OptionEntity {

  @Id
  private String optionCode;
  private String optionDetail;
  private int optionPrice;

}
