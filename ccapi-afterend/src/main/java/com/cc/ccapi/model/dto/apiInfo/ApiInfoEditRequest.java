package com.cc.ccapi.model.dto.apiInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 编辑请求
 *

 */
@Data
public class ApiInfoEditRequest implements Serializable {

    private Long id;

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
     * 接口状态（0 启动，1停止）
     */
    private Integer status;

}