package com.kugring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kugring.back.entity.PointChargeEntity;
import com.kugring.back.entity.UserEntity;


@Repository
public interface PointChargeRepositoy extends JpaRepository<PointChargeEntity, Integer> {

  boolean existsByPointChargeIdAndStatus(int pointChargeId, String status);

  boolean existsByPointChargeId(int pointChargeId);

  PointChargeEntity findByPointChargeId(int pointChargeId);

  PointChargeEntity findByStatus(String stause);

  PointChargeEntity findByUser(UserEntity user);

  PointChargeEntity findByManagerId(int managerId);



}
