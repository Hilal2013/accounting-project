package com.cydeo.repository;

import com.cydeo.entity.ClientVendor;
import com.cydeo.enums.ClientVendorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientVendorRepository extends JpaRepository<ClientVendor,Long> {

   ClientVendor findByIdAndIsDeleted(Long id, Boolean deleted);

   List<ClientVendor> findAllByClientVendorType(ClientVendorType type);


}
