package com.cc.ccapi.model.dto.apiInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cc.ccapi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 查询请求
 *

 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口描述
     */
    private String description;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求头信息
     */
    private String requestHeader;

    /**
     * 响应头信息
     */
    private String responseHeader;

    /**
     * 接口状态（0 启动，1停止）
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long userId;


}