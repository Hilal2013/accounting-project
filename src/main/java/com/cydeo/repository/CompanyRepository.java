package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {


    @Query(value = "SELECT c FROM Company c  ORDER BY c.companyStatus asc, c.title asc")
    List<Company> getCompaniesSortedByStatusAndTitle();

List<Company>  findAllByTitle(String title);



}
//  List<Company> findAllByCompanyStatusIs(CompanyStatus companyStatus);
//    List<Company> findAllByCompanyStatusIsNot(CompanyStatus companyStatus);