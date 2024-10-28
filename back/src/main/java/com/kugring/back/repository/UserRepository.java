package com.kugring.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kugring.back.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  boolean existsByUserId(String userId);

  UserEntity findByUserId(String userId);

  UserEntity findByPin(String pin);

}
