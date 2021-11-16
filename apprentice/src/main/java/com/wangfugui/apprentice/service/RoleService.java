package com.wangfugui.apprentice.service;

import com.wangfugui.apprentice.dao.domain.Role;

import java.util.List;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/10/23
 * @since JDK 1.8.0
 */
public interface RoleService {


    String getUserRole(Integer id);

    List<Role> findUserRole();

    List<Role>  queryRoles();
}
