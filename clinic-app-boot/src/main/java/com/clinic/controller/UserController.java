package com.clinic.controller;

import com.clinic.controller.request.dto.UserRequestDTO;
import com.clinic.controller.search.UserSearchInfo;
import com.clinic.controller.status.JSONStatus;
import com.clinic.entity.dto.UserDTO;
import com.clinic.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(
            @RequestParam(required = false, defaultValue = "1") String page,
            @RequestParam(required = false, defaultValue = "10") String size,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false) List<String> filter) {
        log.info("get users point called");
        UserSearchInfo userSearchInfo = UserSearchInfo.builder()
                .page(Integer.parseInt(page))
                .size(Integer.parseInt(size))
                .sortField(sortField)
                .direction(direction)
                .filter(filter)
                .build();
        return ResponseEntity.ok(userService.getUsers(userSearchInfo));
    }

    @GetMapping(value = "/login")
    public ResponseEntity<UserDTO> checkUserCredentials(@RequestParam String email, @RequestParam String password) {
        log.info("Getting user with login {}", email);
        return ResponseEntity.ok(userService.checkUserCredentials(email, password));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        log.info("Getting car with id {}", id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping
    public ResponseEntity<JSONStatus> saveUser(@RequestBody @Valid UserRequestDTO user) {
        log.info("Saving user");
        int id = userService.saveUser(user);
        log.info("User saved with id {}", id);
        return new ResponseEntity<>(JSONStatus.builder().message(String.valueOf(id)).build(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<JSONStatus> updateUser(@PathVariable int id, @RequestBody @Valid UserRequestDTO user) {
        log.info("Updating user with id {}", id);
        userService.updateUser(id, user);
        log.info("User updated with id {}", id);
        return new ResponseEntity<>(JSONStatus.builder().message(String.valueOf(id)).build(), HttpStatus.OK);
    }
}
