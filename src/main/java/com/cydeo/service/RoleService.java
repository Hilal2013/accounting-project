package com.cydeo.service;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;

import java.util.List;

public interface RoleService {

    RoleDTO findById(Long id);
    List<RoleDTO> listAllRoles();


}
