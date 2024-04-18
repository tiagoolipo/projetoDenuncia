package com.br.projeto.service.role;

import com.br.projeto.entity.role.Role;
import com.br.projeto.entity.role.RoleName;

import java.util.Set;
import java.util.UUID;

public interface RoleService {

    Set<Role> findRoleById(Long id);

    Set<Role> findRoleByName(RoleName roleName);

}
