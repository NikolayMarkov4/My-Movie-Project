package org.softuni.mymoviemaster.service;

import org.softuni.mymoviemaster.domain.models.service.RoleServiceModel;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {
    void seedRolesInDb();
    Set<RoleServiceModel> findAllRoles();
    RoleServiceModel findByAuthority(String authority);
}
