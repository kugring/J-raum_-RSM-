package com.kugring.back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kugring.back.entity.OptionEntity;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, String> {

  // 메뉴ID로 해당 옵션정보 조회
  @Query(value = "SELECT " + "   M.menu_id, O.option_code, O.option_detail, O.option_price " + "   FROM menu AS M "
      + "   INNER JOIN menu_option_list AS MO ON M.menu_id = MO.menu_id " + "   INNER JOIN option_code AS O ON MO.option_code = O.option_code "
      + "   WHERE M.menu_id = ?1 ", nativeQuery = true)
  List<OptionEntity> getOptionList(int menuId);

  // 옵션 코드로 존재하는 옵션 개수를 반환
  int countByOptionCodeIn(List<String> optionCodes);

  OptionEntity findByOptionCode(String optionCode);

  @Query(value = "SELECT option_price FROM option_code WHERE option_code = ?1", nativeQuery = true)
  Optional<Integer> findPriceByOptionCode(String optionCode);
}
