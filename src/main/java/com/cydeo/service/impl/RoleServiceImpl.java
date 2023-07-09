package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import com.cydeo.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {


    private final SecurityService securityService;

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(SecurityService securityService, RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.securityService = securityService;
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }




    @Override
    public RoleDTO findById(Long id)  {

        Role role = roleRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such a role"));

        return mapperUtil.convert(role, new RoleDTO());
    }

    @Override
    public List<RoleDTO> listAllRoles() {

        List<Role> roleList = roleRepository.findAll();
        UserDTO logedInUser = securityService.getLoggedInUser();

        if(logedInUser.getRole().getDescription().equalsIgnoreCase("root user")){

            return roleList.stream().filter(role -> role.getDescription().equalsIgnoreCase("admin")).
                    map(role -> mapperUtil.convert(role, new RoleDTO())).collect(Collectors.toList());

        } else if (logedInUser.getRole().getDescription().equalsIgnoreCase("admin")) {

            return roleList.stream().filter(role -> !role.getDescription().equalsIgnoreCase("root user")).
                    map(role -> mapperUtil.convert(role, new RoleDTO())).collect(Collectors.toList());
        }else return null;


    }
}
