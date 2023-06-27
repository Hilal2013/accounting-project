package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;

public class UserServiceImpl implements UserService {

    private MapperUtil mapperUtil;
    private UserRepository userRepository;

    @Override
    public UserDTO findByUserName(String userName) {

        User user = userRepository.findByUserName(userName);

        return   mapperUtil.convert(user, new UserDTO());
    }


}
