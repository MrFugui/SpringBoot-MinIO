package com.wangfugui.apprentice.dao.mapper;

import com.wangfugui.apprentice.dao.domain.UserBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户/角色/模块关系表 Mapper 接口
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
@Repository
public interface UserBusinessMapper extends BaseMapper<UserBusiness> {

    List<UserBusiness> checkIsValueExist(String type, String userId);

    Integer updateByExampleSelective(String keyId, String type, String btnStr);
}
