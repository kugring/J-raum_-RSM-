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
public class OptionListItem {

  private String optionCode;
  private String optionDetail;
  private int optionPrice;

  public OptionListItem(OptionEntity optionEntity) {
    this.optionCode = optionEntity.getOptionCode();
    this.optionDetail = optionEntity.getOptionDetail();
    this.optionPrice = optionEntity.getOptionPrice();
  }

  public static List<OptionListItem> getList(List<OptionEntity> optionEntities) {
    List<OptionListItem> list = new ArrayList<>();
    for (OptionEntity optionEntity : optionEntities) {
      OptionListItem optionListItem = new OptionListItem(optionEntity);
      list.add(optionListItem);
    }
    return list;
  }
}
