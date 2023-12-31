package com.cydeo.service.impl;


import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final SecurityService securityService;
    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(SecurityService securityService, MapperUtil mapperUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.securityService = securityService;
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
        List<User> userList = userRepository.getUsersSortedByCompanyAndRoleIAndIsDeletedFalse();




        if(securityService.getLoggedInUser().getRole().getDescription().equalsIgnoreCase("admin")) {
            return userList.stream()
                    .filter(user -> user.getCompany().getId().equals(securityService.getLoggedInUser().getCompany().getId()))
                    .map(user -> mapperUtil.convert(user, new UserDTO()))
                    .peek(dto -> {
                        if (dto.getRole().getDescription().equals("Admin"))
                            dto.setOnlyAdmin(this.checkIfOnlyAdminForCompany(dto));
                        else dto.setOnlyAdmin(false);
                    })
                    .collect(Collectors.toList());
        } else if (securityService.getLoggedInUser().getRole().getDescription().equalsIgnoreCase("root user")) {
            return userList.stream()

                    .filter(user -> user.getRole().getDescription().equalsIgnoreCase("admin"))
                    .map(user -> mapperUtil.convert(user, new UserDTO()))
                    .peek(dto -> {
                        if (dto.getRole().getDescription().equals("Admin"))
                            dto.setOnlyAdmin(this.checkIfOnlyAdminForCompany(dto));
                        else dto.setOnlyAdmin(false);
                    })
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private boolean checkIfOnlyAdminForCompany(UserDTO dto) {
        return userRepository.countAllByCompanyAndRole_Description(mapperUtil.convert(dto.getCompany(), new Company()), "Admin")==1;
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
        convertToUser.setEnabled(true);
        convertToUser.setPassword(passwordEncoder.encode(convertToUser.getPassword()));
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

    @Override
    public boolean existByUsername(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) return false;
        return user.getUsername().equalsIgnoreCase(userDTO.getUsername());
    }

    @Override
    public boolean existByUsernameForUpdate(UserDTO userDTO) {
        User user =  userRepository.findByUsername(userDTO.getUsername());
        if (user == null) return false;
        return !user.getId().equals(userDTO.getId());
    }






}
