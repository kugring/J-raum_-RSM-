package com.kugring.back.entity;

import com.kugring.back.entity.primartKey.MenuOptionListPK;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "`menu_option_list`")
public class MenuOptionListEntity {

    @EmbeddedId
    private MenuOptionListPK menuId;

    @MapsId("menuId") // MenuOptionListPK의 menuId에 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    @EmbeddedId
    private MenuOptionListPK optionId;

    @MapsId("optionCode") // MenuOptionListPK의 menuId에 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_code")
    private OptionEntity optionCode;
    
}
