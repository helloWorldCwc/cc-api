package com.cc.ccapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.ccapi.model.dto.apiInfo.ApiInfoQueryRequest;
import com.cc.ccapi.model.entity.ApiInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.ccapi.model.entity.ApiInfo;
import com.cc.ccapi.model.vo.ApiInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author cc
* @description 针对表【api_info(api接口信息表)】的数据库操作Service
* @createDate 2023-12-22 09:18:15
*/
public interface ApiInfoService extends IService<ApiInfo> {

    /**
     * 校验
     *
     * @param apiInfo
     * @param add
     */
    void validApiInfo(ApiInfo apiInfo, boolean add);

    /**
     * 获取查询条件
     *
     * @param apiInfoQueryRequest
     * @return
     */
    QueryWrapper<ApiInfo> getQueryWrapper(ApiInfoQueryRequest apiInfoQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param apiInfo
     * @param request
     * @return
     */
    ApiInfoVO getApiInfoVO(ApiInfo apiInfo, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param apiInfoPage
     * @param request
     * @return
     */
    Page<ApiInfoVO> getApiInfoVOPage(Page<ApiInfo> apiInfoPage, HttpServletRequest request);

}
