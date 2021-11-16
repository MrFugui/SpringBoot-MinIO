package com.wangfugui.apprentice.controller;


import com.alibaba.fastjson.JSONObject;
import com.wangfugui.apprentice.common.util.ResponseUtils;
import com.wangfugui.apprentice.service.IUserBusinessService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 用户/角色/模块关系表 前端控制器
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
@Controller
@RequestMapping("/apprentice/userBusiness")
public class UserBusinessController {

    @Autowired
    private IUserBusinessService userBusinessService;
    /**
     * 校验存在
     *
     * @param type
     * @param keyId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/checkIsValueExist")
    @ApiOperation(value = "校验存在")
    public ResponseUtils checkIsValueExist(@RequestParam(value = "type", required = false) String type,
                                        @RequestParam(value = "keyId", required = false) String keyId) {
        Long id = userBusinessService.checkIsValueExist(type, keyId);
        return ResponseUtils.success(id);
    }

    /**
     * 更新角色的按钮权限
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/updateBtnStr")
    @ApiOperation(value = "更新角色的按钮权限")
    public ResponseUtils updateBtnStr(@RequestBody JSONObject jsonObject) {

        String roleId = jsonObject.getString("roleId");
        String btnStr = jsonObject.getString("btnStr");
        String keyId = roleId;
        String type = "RoleFunctions";
        Integer integer = userBusinessService.updateBtnStr(keyId, type, btnStr);
        if (integer > 0) {

            return ResponseUtils.success();
        } else {
            return ResponseUtils.error();
        }
    }
}

