package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findUserByIdIs(Long id);

    List<User> findAllByIsDeletedOrderByFirstnameDesc(Boolean deleted);


    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);
}
