package com.wangfugui.apprentice.service.impl;

import com.wangfugui.apprentice.dao.domain.UserBusiness;
import com.wangfugui.apprentice.dao.mapper.UserBusinessMapper;
import com.wangfugui.apprentice.service.IUserBusinessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户/角色/模块关系表 服务实现类
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
@Service
public class UserBusinessServiceImpl extends ServiceImpl<UserBusinessMapper, UserBusiness> implements IUserBusinessService {

    @Autowired
    private UserBusinessMapper userBusinessMapper;


    @Override
    public List<UserBusiness> getBasicData(String userId, String type) {


        return userBusinessMapper.checkIsValueExist(type, userId);
    }

    @Override
    public String getUBValueByTypeAndKeyId(String type, String keyId) {
        String ubValue = "";
        List<UserBusiness> ubList = getBasicData(keyId, type);
        if(ubList!=null && ubList.size()>0) {
            ubValue = ubList.get(0).getValue();
        }
        return ubValue;
    }

    @Override
    public Long checkIsValueExist(String type, String keyId) {


        List<UserBusiness> userBusinesses = userBusinessMapper.checkIsValueExist(type, keyId);


        Long id = null;
        if (userBusinesses != null && userBusinesses.size() > 0) {
            id = userBusinesses.get(0).getId();
        }
        return id;
    }

    @Override
    public Integer updateBtnStr(String keyId, String type, String btnStr) {

        return userBusinessMapper.updateByExampleSelective(keyId, type, btnStr);

    }
}
