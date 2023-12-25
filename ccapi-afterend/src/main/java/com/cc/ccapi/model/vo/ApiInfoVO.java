package com.cc.ccapi.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cc.ccapi.model.entity.ApiInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 帖子视图
 *

 */
@Data
public class ApiInfoVO implements Serializable {

    private final static Gson GSON = new Gson();

    /**
     * 主键
     */
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private UserVO userVO;

    /**
     * 包装类转对象
     *
     * @param apiInfoVO
     * @return
     */
    public static ApiInfo voToObj(ApiInfoVO apiInfoVO) {
        if (apiInfoVO == null) {
            return null;
        }
        ApiInfo apiInfo = BeanUtil.copyProperties(apiInfoVO, ApiInfo.class);
        return apiInfo;
    }

    /**
     * 对象转包装类
     *
     * @param apiInfo
     * @return
     */
    public static ApiInfoVO objToVo(ApiInfo apiInfo) {
        if (apiInfo == null) {
            return null;
        }
        ApiInfoVO apiInfoVO = new ApiInfoVO();
        BeanUtils.copyProperties(apiInfo, apiInfoVO);
        return apiInfoVO;
    }
}
