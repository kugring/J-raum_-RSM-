package com.kugring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kugring.back.entity.OrderItemOptionEntity;
import jakarta.transaction.Transactional;

@Repository
public interface OrderItemOptionRepository extends JpaRepository<OrderItemOptionEntity, Integer> {

    @Transactional
    void deleteByOrderItemOptionId(int orderItemOptionId);

}
