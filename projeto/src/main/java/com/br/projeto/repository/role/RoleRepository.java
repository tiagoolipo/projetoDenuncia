package com.br.projeto.repository.role;

import com.br.projeto.entity.role.Role;
import com.br.projeto.entity.role.RoleName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Set<Role>> findAllByRoleName(RoleName name);

    Optional<Set<Role>> findRoleByRoleId(Long roleId);

    @Query("SELECT tr from tb_roles tr where tr.roleName <> 'ADMIN'")
    List<Role> findAllowedCreationRoles();

    @Override
    Page<Role> findAll(Pageable pageable);
}
