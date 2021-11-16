package com.wangfugui.apprentice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangfugui.apprentice.dao.domain.Role;
import com.wangfugui.apprentice.dao.mapper.RoleMapper;
import com.wangfugui.apprentice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MaSiyi
 * @version 1.0.0 2021/10/23
 * @since JDK 1.8.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public String getUserRole(Integer id) {
        return roleMapper.selectOne(new QueryWrapper<Role>().eq("id", id)).getRoleName();
    }

    @Override
    public List<Role> findUserRole() {
        return roleMapper.selectList(null);
    }

    @Override
    public List<Role> queryRoles() {
        return roleMapper.selectList(null);

    }
}
