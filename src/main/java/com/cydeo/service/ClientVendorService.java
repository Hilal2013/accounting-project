package com.cydeo.service;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.enums.InvoiceType;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDTO findById(Long id);

    List<ClientVendorDTO> getListOfClientVendors();
    ClientVendorDTO createClientVendor(ClientVendorDTO clientVendorDTO);
    ClientVendorDTO updateClientVendor(Long id, ClientVendorDTO clientVendorDTO);

    List<ClientVendorDTO> listAllClientVendor(ClientVendorType type);


}
