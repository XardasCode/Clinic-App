package com.clinic.service.impl;

import com.clinic.controller.request.dto.UserRequestDTO;
import com.clinic.controller.search.UserSearchInfo;
import com.clinic.entity.Role;
import com.clinic.entity.User;
import com.clinic.entity.dto.UserDTO;
import com.clinic.entity.dto.mapper.UserDTOMapper;
import com.clinic.entity.specification.UserSpecification;
import com.clinic.exception.ErrorList;
import com.clinic.exception.ServerException;
import com.clinic.repository.RoleRepository;
import com.clinic.repository.UserRepository;
import com.clinic.service.UserService;
import com.clinic.util.UserRoleList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserDTOMapper userMapper;

    @Override
    @Transactional
    public UserDTO getUser(int id) {
        log.debug("Get user by id: {}", id);
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper)
                .orElseThrow(() -> new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public int saveUser(UserRequestDTO userDTO) {
        log.debug("Save user: {}", userDTO);
        User user = User.buildUserFromDTO(userDTO);
        Role userRole = roleRepository.findById(UserRoleList.USER.getId())
                .orElseThrow(() -> new ServerException("Role with id " + UserRoleList.USER.getId() + " not found", ErrorList.ROLE_NOT_FOUND));
        user.setRole(userRole);
        log.debug("Save user: {}", user);
        return userRepository.save(user).getId();
    }

    @Override
    @Transactional
    public void updateUser(int id, UserRequestDTO userDTO) {
        log.debug("Update user with id: {}, user: {}", id, userDTO);
        User user = userRepository.findById(id).orElseThrow(
                () -> new ServerException("User with id " + id + " not found", ErrorList.USER_NOT_FOUND));
        User updatedUser = User.buildUserFromDTO(userDTO);
        User.margeUser(user, updatedUser);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public List<UserDTO> getUsers(UserSearchInfo info) {
        log.debug("Get users: {}", info);
        Specification<User> specification = new UserSpecification(info);
        return userRepository.findAll(specification, Pageable.ofSize(info.getSize()).withPage(info.getPage() - 1))
                .stream()
                .map(userMapper)
                .toList();
    }

    @Override
    @Transactional
    public UserDTO checkUserCredentials(String email, String password) {
        log.debug("Check user {} credentials", email);
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            throw new ServerException("Email or password is null", ErrorList.BAD_REQUEST);
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ServerException("User with email " + email + " not found", ErrorList.USER_NOT_FOUND);
        }
        if (!user.getPassword().equals(password)) {
            throw new ServerException("Wrong password", ErrorList.WRONG_PASSWORD);
        }
        return userMapper.apply(user);
    }

    @Override
    @Transactional
    public Integer getUserPageCount(UserSearchInfo userSearchInfo) {
        log.debug("Get user page count: {}", userSearchInfo);
        Specification<User> specification = new UserSpecification(userSearchInfo);
        int size = userRepository.findAll(specification).size();
        return size % userSearchInfo.getSize() == 0 ? size / userSearchInfo.getSize() : size / userSearchInfo.getSize() + 1;
    }
}
