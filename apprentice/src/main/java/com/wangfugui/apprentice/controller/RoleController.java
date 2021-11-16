package com.wangfugui.apprentice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangfugui.apprentice.common.util.ResponseUtils;
import com.wangfugui.apprentice.dao.domain.Role;
import com.wangfugui.apprentice.service.IUserBusinessService;
import com.wangfugui.apprentice.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author masiyi
 */
@RestController
@RequestMapping(value = "/role")
@Api(tags = {"角色管理"})
public class RoleController {

    @Autowired
    private RoleService sysRoleService;

    @Autowired
    private IUserBusinessService userBusinessService;

    /**
     * 角色对应应用显示
     * @return
     */
    @GetMapping(value = "/findUserRole")
    @ApiOperation(value = "查询用户的角色")
    public ResponseUtils findUserRole(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId){
        JSONArray arr = new JSONArray();
        try {
            //获取权限信息
            String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, keyId);
            List<Role> dataList = sysRoleService.findUserRole();
            if (null != dataList) {
                for (Role role : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", role.getId());
                    item.put("text", role.getRoleName());
                    Boolean flag = ubValue.contains("[" + role.getId().toString() + "]");
                    if (flag) {
                        item.put("checked", true);
                    }
                    arr.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtils.success(arr);
    }

    @GetMapping(value = "/allList")
    @ApiOperation(value = "查询全部角色列表")
    public ResponseUtils allList() {
        return ResponseUtils.success(sysRoleService.queryRoles());
    }
}
