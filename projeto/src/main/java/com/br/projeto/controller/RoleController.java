package com.br.projeto.controller;

import com.br.projeto.entity.role.Role;
import com.br.projeto.service.role.RoleServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/roles")
public class RoleController {
    
    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles(Pageable pageable) {
        List<Role> role = roleService.getRoles(pageable);
        return ResponseEntity.ok(role);
    }

}
