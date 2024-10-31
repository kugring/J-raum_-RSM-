package com.kugring.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kugring.back.entity.OptionEntity;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, Integer> {

  // 메뉴ID로 해당 옵션정보 조회
  @Query(value = "SELECT " 
      + "   M.menu_id, O.* " 
      + "   FROM menu AS M "
      + "   INNER JOIN menu_option_list AS MO ON M.menu_id = MO.menu_id " 
      + "   INNER JOIN option_list AS O ON MO.option_id = O.option_id "
      + "   WHERE M.menu_id = ?1 ", nativeQuery = true)
  List<OptionEntity> getOptionList(int menuId);

  // 옵션 코드로 존재하는 옵션 개수를 반환
  int countByOptionIdIn(List<Integer> optionIds);

  OptionEntity findByOptionId(int optionId);

  // 옵션의 가격만 가져오는 함수
  @Query("SELECT o.optionPrice FROM OptionEntity o WHERE o.optionId = :optionId")
  List<Integer> findPriceByOptionId(@Param("optionId") int optionId);

  boolean existsByOptionDetail(String optionDetail);
}
