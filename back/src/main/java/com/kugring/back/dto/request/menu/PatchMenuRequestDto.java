package com.kugring.back.dto.request.menu;

import java.util.List;
import com.kugring.back.dto.object.MenuOptionListObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchMenuRequestDto {
    
    private int status;
    private int sequence;
    private int menuPrice;
    private int espressoShot;
    
    private String image;
    private String menuName;
    private String category;
    private String temperature;

    private List<MenuOptionListObject> options;
}
