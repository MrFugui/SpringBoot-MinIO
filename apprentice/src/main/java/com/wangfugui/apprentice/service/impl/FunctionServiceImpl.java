package com.wangfugui.apprentice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangfugui.apprentice.dao.domain.Function;
import com.wangfugui.apprentice.dao.mapper.FunctionMapper;
import com.wangfugui.apprentice.service.IFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 功能模块表 服务实现类
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
@Service
public class FunctionServiceImpl extends ServiceImpl<FunctionMapper, Function> implements IFunctionService {

    @Autowired
    private FunctionMapper functionMapper;

    @Override
    public List<Function> getRoleFunction(String pNumber) {
        return functionMapper.getRoleFunction(pNumber);
    }

    @Override
    public List<Function> findRoleFunction(String pNumber) {
        return functionMapper.getRoleFunction(pNumber);
    }

    @Override
    public List<Function> findByIds(String functionsIds) {
        List<Long> idList = this.strToLongList(functionsIds);

        return functionMapper.findByIds(idList);
    }

    public List<Long> strToLongList(String strArr) {
        List<Long> idList=new ArrayList<Long>();
        String[] d=strArr.split(",");
        for (int i = 0, size = d.length; i < size; i++) {
            if(d[i]!=null) {
                idList.add(Long.parseLong(d[i]));
            }
        }
        return idList;
    }
}
