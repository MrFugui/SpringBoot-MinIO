package com.wangfugui.apprentice.service;

import com.wangfugui.apprentice.dao.domain.Function;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 功能模块表 服务类
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
public interface IFunctionService extends IService<Function> {

    List<Function> getRoleFunction(String pNumber);

    List<Function> findRoleFunction(String s);

    List<Function> findByIds(String funIds);
}
