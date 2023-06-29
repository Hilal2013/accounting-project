package com.cydeo.service.impl;


import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;

    public UserServiceImpl(MapperUtil mapperUtil, UserRepository userRepository) {
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO findByUsername(String userName) {

        User user = userRepository.findByUsername(userName);

        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public UserDTO findById(Long id) {
        User userId = userRepository.findUserByIdIs(id);
        return mapperUtil.convert(userId, new UserDTO());
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstnameDesc(false);
        return userList.stream().map(user -> mapperUtil.convert(user, new UserDTO())).collect(Collectors.toList());
    }

    @Override
    public void save(UserDTO user) {
        userRepository.save(mapperUtil.convert(user,new User()));
    }

    @Override
    public UserDTO update(UserDTO user) {
        User user1 = userRepository.findByIdAndAndIsDeleted(user.getId(),false);
        User convertToUser = mapperUtil.convert(user,new User());
        convertToUser.setId(user1.getId());

        userRepository.save(convertToUser);
        return findByUsername(user.getUsername());
    }

    @Override
    public void delete(Long id) {

        User user = userRepository.findByIdAndAndIsDeleted(id,false);

            user.setIsDeleted(true);
            user.setUsername(user.getUsername()+"-"+ user.getId());
            userRepository.save(user);
        }



}
