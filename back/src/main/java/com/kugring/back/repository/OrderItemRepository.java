package com.kugring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kugring.back.entity.OrderItemEntity;
import jakarta.transaction.Transactional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {

    @Transactional
    void deleteByOrderItemId(int OrderItemId);
}

