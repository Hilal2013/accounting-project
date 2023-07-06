package com.cydeo.service.impl;


import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(MapperUtil mapperUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
      User user2 =  mapperUtil.convert(user,new User());
      user2.setEnabled(true);
      user2.setPassword(passwordEncoder.encode(user2.getPassword()));
      userRepository.save(user2);
    }

    @Override
    public UserDTO update(UserDTO user) {
        User user1 = userRepository.findUserByIdIs(user.getId());
        User convertToUser = mapperUtil.convert(user,new User());
        convertToUser.setId(user1.getId());

        userRepository.save(convertToUser);
        return findByUsername(user.getUsername());
    }

    @Override
    public void delete(Long id) {

        User user = userRepository.findUserByIdIs(id);

            user.setIsDeleted(true);
            user.setUsername(user.getUsername()+"-"+ user.getId());
            userRepository.save(user);
        }



}
