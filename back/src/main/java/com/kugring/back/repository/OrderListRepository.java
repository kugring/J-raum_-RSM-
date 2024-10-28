package com.kugring.back.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.kugring.back.entity.OrderListEntity;

@Repository
public interface OrderListRepository extends JpaRepository<OrderListEntity, Integer> {

  // userId, orderStatus, createDate, completeDate 동적 필터링
  @Query(value = "SELECT o.* FROM order_list o " 
      + "LEFT JOIN order_item oi ON o.order_list_id = oi.order_list_id "
      + "LEFT JOIN order_item_option oio ON oi.order_item_id = oio.order_item_id " 
      + "LEFT JOIN menu m ON oi.menu_id = m.menu_id "
      + "WHERE (:userId IS NULL OR o.user_id = :userId) " 
      + "AND (:orderStatus IS NULL OR o.order_status = :orderStatus) "
      + "AND (:startCreateDate IS NULL OR o.create_order_date >= :startCreateDate) "
      + "AND (:endCreateDate IS NULL OR o.create_order_date <= :endCreateDate) "
      + "AND (:startCompleteDate IS NULL OR o.complete_order_date >= :startCompleteDate) "
      + "AND (:endCompleteDate IS NULL OR o.complete_order_date <= :endCompleteDate)", nativeQuery = true)
  List<OrderListEntity> findOrders(@Param("userId") String userId, @Param("orderStatus") String orderStatus,
      @Param("startCreateDate") LocalDateTime startCreateDate, @Param("endCreateDate") LocalDateTime endCreateDate,
      @Param("startCompleteDate") LocalDateTime startCompleteDate, @Param("endCompleteDate") LocalDateTime endCompleteDate);



  @Query("SELECT ol FROM OrderListEntity ol " + "JOIN FETCH ol.orderItems oi " + "JOIN FETCH oi.menu " + "WHERE ol.orderListId = :orderListId")
  OrderListEntity findByOrderListId(@Param("orderListId") Integer orderListId);



  boolean existsByOrderListId(Integer orderListId);



}
