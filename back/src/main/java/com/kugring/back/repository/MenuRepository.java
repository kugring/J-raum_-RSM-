package com.kugring.back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kugring.back.entity.MenuEntity;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

  // 메뉴 ID로 존재하는 메뉴 개수를 반환
  int countByMenuIdIn(List<Integer> menuIds);


  MenuEntity findByMenuId(int menuId);


  @Query(value = "SELECT menu_price FROM menu WHERE menu_id = ?1", nativeQuery = true)
  Optional<Integer> findPriceByMenuId(int menuId);
}
