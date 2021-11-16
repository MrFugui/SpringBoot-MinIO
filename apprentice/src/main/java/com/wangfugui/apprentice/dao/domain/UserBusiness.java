package com.wangfugui.apprentice.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户/角色/模块关系表
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
@Data
@TableName("user_business")
@ApiModel(value = "UserBusiness对象", description = "用户/角色/模块关系表")
public class UserBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("类别")
    private String type;

    @ApiModelProperty("主id")
    private String keyId;

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("按钮权限")
    private String btnStr;

    @ApiModelProperty("删除标记，0未删除，1删除")
    private String deleteFlag;


}
