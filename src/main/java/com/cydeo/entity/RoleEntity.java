package com.cydeo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    private String description;

}
