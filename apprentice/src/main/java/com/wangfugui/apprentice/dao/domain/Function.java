package com.wangfugui.apprentice.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 功能模块表
 * </p>
 *
 * @author MrFugui
 * @since 2021-11-13
 */
@Data
@ApiModel(value = "Function对象", description = "功能模块表")
public class Function implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("编号")
    private String number;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("上级编号")
    private String parentNumber;

    @ApiModelProperty("链接")
    private String url;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("收缩")
    private Boolean state;

    @ApiModelProperty("排序")
    private String sort;

    @ApiModelProperty("启用")
    private Boolean enabled;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("功能按钮")
    private String pushBtn;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("删除标记，0未删除，1删除")
    private String deleteFlag;


}
