package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findUserByIdIs(Long id);



    @Query("select s from User s where s.isDeleted=false order by s.company.title asc , s.role.description asc")
    List<User> getUsersSortedByCompanyAndRoleIAndIsDeletedFalse();






    int countAllByCompanyAndRole_Description(Company company, String roleDisc);
}
