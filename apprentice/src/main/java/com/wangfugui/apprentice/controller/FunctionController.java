package com.wangfugui.apprentice.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangfugui.apprentice.common.util.ResponseUtils;
import com.wangfugui.apprentice.dao.domain.Function;
import com.wangfugui.apprentice.dao.domain.UserBusiness;
import com.wangfugui.apprentice.service.IFunctionService;
import com.wangfugui.apprentice.service.IUserBusinessService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能模块表 前端控制器
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
@Controller
@RequestMapping("/apprentice/function")
@Slf4j
public class FunctionController {


    @Autowired
    private IFunctionService functionService;

    @Autowired
    private IUserBusinessService userBusinessService;


    /**
     * 根据父编号查询菜单
     *
     * @param jsonObject
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findMenuByPNumber")
    @ApiOperation(value = "根据父编号查询菜单")
    public ResponseUtils findMenuByPNumber(@RequestBody JSONObject jsonObject,
                                           HttpServletRequest request) throws Exception {
        String pNumber = jsonObject.getString("pNumber");
        String userId = jsonObject.getString("userId");
        //存放数据json数组
        JSONArray dataArray = new JSONArray();
        try {
            Long roleId = 0L;
            String fc = "";
            List<UserBusiness> roleList = userBusinessService.getBasicData(userId, "UserRole");
            if (roleList != null && roleList.size() > 0) {
                String value = roleList.get(0).getValue();
                if (StringUtils.isNotEmpty(value)) {
                    String roleIdStr = value.replace("[", "").replace("]", "");
                    roleId = Long.parseLong(roleIdStr);
                }
            }
            //当前用户所拥有的功能列表，格式如：[1][2][5]
            List<UserBusiness> funList = userBusinessService.getBasicData(roleId.toString(), "RoleFunctions");
            if (funList != null && funList.size() > 0) {
                fc = funList.get(0).getValue();
            }
            List<Function> dataList = functionService.getRoleFunction(pNumber);
            if (dataList.size() != 0) {
                dataArray = getMenuByFunction(dataList, fc);
                //增加首页菜单项
                JSONObject homeItem = new JSONObject();
                homeItem.put("id", 0);
                homeItem.put("text", "首页");
                homeItem.put("icon", "home");
                homeItem.put("url", "/dashboard/analysis");
                homeItem.put("component", "/layouts/TabLayout");
                dataArray.add(0, homeItem);
            }
        } catch (DataAccessException e) {
            log.error(">>>>>>>>>>>>>>>>>>>查找异常", e);
            return ResponseUtils.error();
        }
        return ResponseUtils.success(dataArray);
    }

    public JSONArray getMenuByFunction(List<Function> dataList, String fc) throws Exception {
        JSONArray dataArray = new JSONArray();
        for (Function function : dataList) {
            JSONObject item = new JSONObject();
            List<Function> newList = functionService.getRoleFunction(function.getNumber());
            item.put("id", function.getId());
            item.put("text", function.getName());
            item.put("icon", function.getIcon());
            item.put("url", function.getUrl());
            item.put("component", function.getComponent());
            if (newList.size() > 0) {
                JSONArray childrenArr = getMenuByFunction(newList, fc);
                if (childrenArr.size() > 0) {
                    item.put("children", childrenArr);
                    dataArray.add(item);
                }
            } else {
                if (fc.contains("[" + function.getId().toString() + "]")) {
                    dataArray.add(item);
                }
            }
        }
        return dataArray;
    }

    /**
     * 角色对应功能显示
     *
     * @return
     */
    @GetMapping(value = "/findRoleFunction")
    @ApiOperation(value = "角色对应功能显示")
    public JSONArray findRoleFunction(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId) {
        JSONArray arr = new JSONArray();
        try {
            List<Function> dataListFun = functionService.findRoleFunction("0");
            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 0);
            outer.put("key", 0);
            outer.put("value", 0);
            outer.put("title", "功能列表");
            outer.put("attributes", "功能列表");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataListFun) {
                List<Function> dataList = new ArrayList<>(dataListFun);
                dataArray = getFunctionList(dataList, type, keyId);
                outer.put("children", dataArray);
            }
            arr.add(outer);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public JSONArray getFunctionList(List<Function> dataList, String type, String keyId) throws Exception {
        JSONArray dataArray = new JSONArray();
        //获取权限信息
        String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, keyId);
        if (null != dataList) {
            for (Function function : dataList) {
                JSONObject item = new JSONObject();
                item.put("id", function.getId());
                item.put("key", function.getId());
                item.put("value", function.getId());
                item.put("title", function.getName());
                item.put("attributes", function.getName());
                List<Function> funList = functionService.findRoleFunction(function.getNumber());
                if (funList.size() > 0) {
                    JSONArray funArr = getFunctionList(funList, type, keyId);
                    item.put("children", funArr);
                    dataArray.add(item);
                } else {
                    Boolean flag = ubValue.contains("[" + function.getId().toString() + "]");
                    item.put("checked", flag);
                    dataArray.add(item);
                }
            }
        }
        return dataArray;
    }

    /**
     * 根据id列表查找功能信息
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/findRoleFunctionsById")
    @ApiOperation(value = "根据id列表查找功能信息")
    public ResponseUtils findByIds(@RequestParam("roleId") Long roleId) {
        try {
            List<UserBusiness> list = userBusinessService.getBasicData(roleId.toString(), "RoleFunctions");
            if (null != list && list.size() > 0) {
                //按钮
                Map<Long, String> btnMap = new HashMap<>();
                String btnStr = list.get(0).getBtnStr();
                if (StringUtils.isNotEmpty(btnStr)) {
                    JSONArray btnArr = JSONArray.parseArray(btnStr);
                    for (Object obj : btnArr) {
                        JSONObject btnObj = JSONObject.parseObject(obj.toString());
                        if (btnObj.get("funId") != null && btnObj.get("btnStr") != null) {
                            btnMap.put(btnObj.getLong("funId"), btnObj.getString("btnStr"));
                        }
                    }
                }
                //菜单
                String funIds = list.get(0).getValue();
                funIds = funIds.substring(1, funIds.length() - 1);
                funIds = funIds.replace("][", ",");
                List<Function> dataList = functionService.findByIds(funIds);
                JSONObject outer = new JSONObject();
                outer.put("total", dataList.size());
                //存放数据json数组
                JSONArray dataArray = new JSONArray();
                if (null != dataList) {
                    for (Function function : dataList) {
                        JSONObject item = new JSONObject();
                        item.put("id", function.getId());
                        item.put("name", function.getName());
                        item.put("pushBtn", function.getPushBtn());
                        item.put("btnStr", btnMap.get(function.getId()));
                        dataArray.add(item);
                    }
                }
                outer.put("rows", dataArray);

            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return ResponseUtils.error();

        }
        return ResponseUtils.success();
    }
}

