package com.kugring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kugring.back.entity.PointChargeEntity;


@Repository
public interface PointChargeRepositoy extends JpaRepository<PointChargeEntity, Integer> {

  boolean existsByPointChargeIdAndStatus(int pointChargeId, String status);

  boolean existsByPointChargeId(int pointChargeId);

  PointChargeEntity findByPointChargeId(int pointChargeId);

  PointChargeEntity findByStatus(String stause);

  PointChargeEntity findByUserId(String userId);

  PointChargeEntity findByManagerId(int managerId);



}
