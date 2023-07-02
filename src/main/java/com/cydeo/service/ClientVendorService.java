package com.cydeo.service;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDTO findById(Long id);

    List<ClientVendorDTO> getListOfClientVendors();

    ClientVendorDTO createClientVendor(ClientVendorDTO clientVendorDTO);

    ClientVendorDTO updateClientVendor(Long id, ClientVendorDTO clientVendorDTO);

    void delete(Long id);

    List<ClientVendorDTO>  listAllClientVendor(ClientVendorType vendor);
}
