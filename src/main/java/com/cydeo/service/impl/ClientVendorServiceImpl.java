package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {
    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository,
                                   MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ClientVendorDTO findById(Long id) {
        return mapperUtil.convert(clientVendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClientVendor couldn't find.")), new ClientVendorDTO());
    }

    @Override
    public List<ClientVendorDTO> getListOfClientVendors() {
        return clientVendorRepository.findAll()
                .stream()
                .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public ClientVendorDTO createClientVendor(ClientVendorDTO clientVendorDTO) {
        ClientVendor clientVendor = clientVendorRepository.save(mapperUtil.convert(clientVendorDTO, new ClientVendor()));
        return mapperUtil.convert(clientVendor, new ClientVendorDTO());
    }

    @Override
    public ClientVendorDTO updateClientVendor(Long id, ClientVendorDTO clientVendorDTO) {
        ClientVendor clientVendor = clientVendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClientVendor couldn't find"));
        ClientVendor convertedClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        convertedClientVendor.setId(clientVendor.getId());
        clientVendorRepository.save(convertedClientVendor);
        return mapperUtil.convert(convertedClientVendor, new ClientVendorDTO());
    }

    //soft delete
    @Override
    public void delete(Long id) {

        ClientVendor clientVendor = clientVendorRepository.findByIdAndIsDeleted(id, false);
        clientVendor.setIsDeleted(true);
        clientVendorRepository.save(clientVendor);

    }

}
