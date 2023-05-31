package com.clinic.service;

import com.clinic.controller.request.dto.UserRequestDTO;
import com.clinic.controller.search.UserSearchInfo;
import com.clinic.entity.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUser(int id);

    int saveUser(UserRequestDTO user);

    void updateUser(int id, UserRequestDTO user);

    List<UserDTO> getUsers(UserSearchInfo userSearchInfo);
}
