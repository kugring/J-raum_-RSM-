package com.kugring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kugring.back.entity.ManagerEntity;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Integer> {

  boolean existsByManagerId(int managerId);


}
