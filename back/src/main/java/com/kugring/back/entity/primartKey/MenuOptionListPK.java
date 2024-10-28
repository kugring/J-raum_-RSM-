package com.kugring.back.entity.primartKey;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuOptionListPK implements Serializable {
    
    private String optionCode;
    private int menuId; // 기본 키는 기본 데이터 타입으로 설정합니다.
}
