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
public class OptionObject {

  private int status;
  private int sequence;
  private int optionId;
  private int optionPrice;

  private String category;
  private String optionDetail;


  public OptionObject(OptionEntity optionEntity) {
    this.status = optionEntity.getStatus();
    this.optionId = optionEntity.getOptionId();
    this.sequence = optionEntity.getSequence();
    this.category = optionEntity.getCategory();
    this.optionPrice = optionEntity.getOptionPrice();
    this.optionDetail = optionEntity.getOptionDetail();
    this.optionDetail = optionEntity.getOptionDetail();
  }

  public static List<OptionObject> getList(List<OptionEntity> optionEntities) {
    List<OptionObject> list = new ArrayList<>();
    for (OptionEntity optionEntity : optionEntities) {
      OptionObject optionListItem = new OptionObject(optionEntity);
      list.add(optionListItem);
    }
    return list;
  }
}
