package com.kugring.back.dto.object;

import java.util.ArrayList;
import java.util.List;

import com.kugring.back.entity.OptionEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionCodeObject {

  private String optionCode;
  private String optionDetail;
  private int optionPrice;

  public OptionCodeObject(OptionEntity optionEntity) {
    this.optionCode = optionEntity.getOptionCode();
    this.optionDetail = optionEntity.getOptionDetail();
    this.optionPrice = optionEntity.getOptionPrice();
  }

  public static List<OptionCodeObject> getList(List<OptionEntity> optionEntities) {
    List<OptionCodeObject> list = new ArrayList<>();
    for (OptionEntity optionEntity : optionEntities) {
      OptionCodeObject optionListItem = new OptionCodeObject(optionEntity);
      list.add(optionListItem);
    }
    return list;
  }
}
