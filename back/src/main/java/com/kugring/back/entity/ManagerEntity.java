package com.kugring.back.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "`manager`")
@Table(name = "`manager`")
public class ManagerEntity {

  @Id
  private int managerId;
  private String managerName;
  private String position;
  private String password;
  private String pin;

}
