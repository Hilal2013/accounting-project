package com.cydeo.repository;

import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.enums.ClientVendorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {

    ClientVendor findByIdAndIsDeleted(Long id, Boolean deleted);

    List<ClientVendor> findAllByClientVendorTypeAndCompanyOrderByClientVendorNameAsc(ClientVendorType type, Company company);

    @Query("Select c from ClientVendor c Order By c.clientVendorType asc,c.clientVendorName asc")
    List<ClientVendor> getAllClientVendorsSortByTypeAndName();

    ClientVendor findByClientVendorNameAndCompany(String clientVendorName,Company company);
    @Query("Select cv from ClientVendor cv  where cv.company.title =?1  Order By cv.clientVendorType asc,cv.clientVendorName asc")
    List<ClientVendor> findAllByCompanyTitleAndSortByTypeAndName(String companyTitle);
}
