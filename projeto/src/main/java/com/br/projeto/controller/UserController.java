package com.br.projeto.controller;

import com.br.projeto.dto.user.UserCreateRequestDTO;
import com.br.projeto.dto.user.UserResponseDTO;
import com.br.projeto.entity.user.User;
import com.br.projeto.service.role.RoleServiceImpl;
import com.br.projeto.service.user.UserServiceImpl;
import com.br.projeto.utils.MapperUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final MapperUtils mapperUtils = new MapperUtils();
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createSecretary(@Validated @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        var userRole = roleService.findRoleById(userCreateRequestDTO.getRoleId());
        var userEntity = mapperUtils.map(userCreateRequestDTO, User.class);
        userEntity.setRoles(userRole);

        var createdUser = userService.createUser(userEntity);

        var createdUserResponseDTO = mapperUtils.map(createdUser, UserResponseDTO.class);

        return ResponseEntity.ok(createdUserResponseDTO);
    }
}
