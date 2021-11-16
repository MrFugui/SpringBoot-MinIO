package com.wangfugui.apprentice.dao.mapper;

import com.wangfugui.apprentice.dao.domain.Function;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 功能模块表 Mapper 接口
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
@Repository
public interface FunctionMapper extends BaseMapper<Function> {

    List<Function> getRoleFunction(String pNumber);

    List<Function> findByIds(List<Long> idList);
}
