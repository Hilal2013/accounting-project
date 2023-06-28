package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);
    List<User> findAllByIsDeletedOrderByFirstnameDesc(Boolean deleted);

    User findByUsernameAndIsDeleted(String username, Boolean deleted);
    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);
}
