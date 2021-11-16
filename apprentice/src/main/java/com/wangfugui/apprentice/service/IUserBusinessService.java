package com.wangfugui.apprentice.service;

import com.wangfugui.apprentice.dao.domain.UserBusiness;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户/角色/模块关系表 服务类
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
public interface IUserBusinessService extends IService<UserBusiness> {

    Long checkIsValueExist(String type, String keyId);

    List<UserBusiness> getBasicData(String userId, String userRole);

    String getUBValueByTypeAndKeyId(String type, String keyId);

    Integer updateBtnStr(String keyId, String type, String btnStr);
}
