package com.kugring.back.entity;

import com.kugring.back.dto.request.option.PatchOptionRequestDto;
import com.kugring.back.dto.request.option.PostOptionRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "`option_list`")
public class OptionEntity {

  @Id 
  private int optionId;
  private int sequence;
  private int status;
  private int optionPrice;
  private String category;
  private String optionDetail;

  
  public OptionEntity(PostOptionRequestDto dto){
    this.status = dto.getStatus();
    this.sequence = dto.getSequence();
    this.category = dto.getCategory();
    this.optionPrice = dto.getOptionPrice();
    this.optionDetail = dto.getOptionDetail();
  }


    public void patchOption(PatchOptionRequestDto dto){
    this.status = dto.getStatus();
    this.sequence = dto.getSequence();
    this.category = dto.getCategory();
    this.optionPrice = dto.getOptionPrice();
    this.optionDetail = dto.getOptionDetail();
  }
  
}
