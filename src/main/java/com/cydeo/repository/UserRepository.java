package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findUserByIdIs(Long id);

    List<User> findAllByIsDeletedOrderByFirstnameDesc(Boolean deleted);


    @Query("select s from User s order by s.company.title asc, s.role.description asc ")
    List<User> getUsersSortedByCompanyAndRole();


    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);
}
