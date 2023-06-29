package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findByUsername(String userName);

    UserDTO findById(Long id);

    List<UserDTO> listAllUsers();

    void save(UserDTO user);

    UserDTO update(UserDTO user);

    void delete(Long id);


}

