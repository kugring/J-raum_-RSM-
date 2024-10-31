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
  @Query("SELECT o FROM OrderListEntity o " +
        // "LEFT JOIN FETCH o.orderItems oi " + 이런거 조인 안해도 알아서 가져옴
        // "LEFT JOIN FETCH oi.orderItemOptions oio " +
        // "LEFT JOIN FETCH oi.menu m " +
        "WHERE (:userId IS NULL OR o.user.userId = :userId) " +
        "AND (:orderStatus IS NULL OR o.orderStatus = :orderStatus) " +
        "AND (:startCreateDate IS NULL OR o.createOrderDate >= :startCreateDate) " +
        "AND (:endCreateDate IS NULL OR o.createOrderDate <= :endCreateDate) " +
        "AND (:startCompleteDate IS NULL OR o.completeOrderDate >= :startCompleteDate) " +
        "AND (:endCompleteDate IS NULL OR o.completeOrderDate <= :endCompleteDate)")
  List<OrderListEntity> findOrders(@Param("userId") String userId, 
                                    @Param("orderStatus") String orderStatus,
                                    @Param("startCreateDate") LocalDateTime startCreateDate, 
                                    @Param("endCreateDate") LocalDateTime endCreateDate,
                                    @Param("startCompleteDate") LocalDateTime startCompleteDate, 
                                    @Param("endCompleteDate") LocalDateTime endCompleteDate);




  @Query("SELECT ol FROM OrderListEntity ol " + "JOIN FETCH ol.orderItems oi " + "JOIN FETCH oi.menu " + "WHERE ol.orderListId = :orderListId")
  OrderListEntity findByOrderListId(@Param("orderListId") Integer orderListId);


}
