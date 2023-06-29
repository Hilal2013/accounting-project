package com.cydeo.service;

import com.cydeo.dto.ClientVendorDTO;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDTO findById(Long id);

    List<ClientVendorDTO> getListOfClientVendors();
    ClientVendorDTO createClientVendor(ClientVendorDTO clientVendorDTO);
    ClientVendorDTO updateClientVendor(Long id, ClientVendorDTO clientVendorDTO);

}
