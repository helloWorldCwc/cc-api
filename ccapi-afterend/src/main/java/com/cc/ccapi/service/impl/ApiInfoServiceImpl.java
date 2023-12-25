package com.cc.ccapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.ccapi.common.ErrorCode;
import com.cc.ccapi.constant.CommonConstant;
import com.cc.ccapi.exception.BusinessException;
import com.cc.ccapi.exception.ThrowUtils;
import com.cc.ccapi.model.dto.apiInfo.ApiInfoQueryRequest;
import com.cc.ccapi.model.entity.*;
import com.cc.ccapi.model.enums.RequestMethodEnum;
import com.cc.ccapi.model.vo.ApiInfoVO;
import com.cc.ccapi.model.vo.UserVO;
import com.cc.ccapi.service.ApiInfoService;
import com.cc.ccapi.mapper.ApiInfoMapper;
import com.cc.ccapi.service.UserService;
import com.cc.ccapi.utils.SqlUtils;
import com.google.gson.Gson;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author cc
* @description 针对表【api_info(api接口信息表)】的数据库操作Service实现
* @createDate 2023-12-22 09:18:15
*/
@Service
public class ApiInfoServiceImpl extends ServiceImpl<ApiInfoMapper, ApiInfo>
    implements ApiInfoService {

    private final static Gson GSON = new Gson();

    @Resource
    private UserService userService;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void validApiInfo(ApiInfo apiInfo, boolean add) {
        if (apiInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = apiInfo.getName();
        String description = apiInfo.getDescription();
        String method = apiInfo.getMethod();
        String url = apiInfo.getUrl();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, description, method, url ), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if(RequestMethodEnum.getEnumByValue(method) == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求方式不对");
        }
    }

    /**
     * 获取查询包装类
     *
     * @param apiInfoQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<ApiInfo> getQueryWrapper(ApiInfoQueryRequest apiInfoQueryRequest) {
        QueryWrapper<ApiInfo> queryWrapper = new QueryWrapper<>();
        if (apiInfoQueryRequest == null) {
            return queryWrapper;
        }
        String name = apiInfoQueryRequest.getName();
        String description = apiInfoQueryRequest.getDescription();
        String method = apiInfoQueryRequest.getMethod();
        String url = apiInfoQueryRequest.getUrl();
        String requestHeader = apiInfoQueryRequest.getRequestHeader();
        String responseHeader = apiInfoQueryRequest.getResponseHeader();
        Integer status = apiInfoQueryRequest.getStatus();
        Long userId = apiInfoQueryRequest.getUserId();
        String sortField = apiInfoQueryRequest.getSortField();
        String sortOrder = apiInfoQueryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.like(StringUtils.isNotBlank(url), "url", url);
        queryWrapper.like(StringUtils.isNotBlank(method), "method", method);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public ApiInfoVO getApiInfoVO(ApiInfo apiInfo, HttpServletRequest request) {
        ApiInfoVO apiInfoVO = ApiInfoVO.objToVo(apiInfo);
        long apiInfoId = apiInfo.getId();
        // 1. 关联查询用户信息
        Long userId = apiInfo.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        apiInfoVO.setUserVO(userVO);
        // 2. 已登录
        User loginUser = userService.getLoginUserPermitNull(request);
        return apiInfoVO;
    }

    @Override
    public Page<ApiInfoVO> getApiInfoVOPage(Page<ApiInfo> apiInfoPage, HttpServletRequest request) {
        List<ApiInfo> apiInfoList = apiInfoPage.getRecords();
        Page<ApiInfoVO> apiInfoVOPage = new Page<>(apiInfoPage.getCurrent(), apiInfoPage.getSize(), apiInfoPage.getTotal());
        if (CollectionUtils.isEmpty(apiInfoList)) {
            return apiInfoVOPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = apiInfoList.stream().map(ApiInfo::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        User loginUser = userService.getLoginUserPermitNull(request);
        // 填充信息
        List<ApiInfoVO> apiInfoVOList = apiInfoList.stream().map(apiInfo -> {
            ApiInfoVO apiInfoVO = ApiInfoVO.objToVo(apiInfo);
            Long userId = apiInfo.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            apiInfoVO.setUserVO(userService.getUserVO(user));
            return apiInfoVO;
        }).collect(Collectors.toList());
        apiInfoVOPage.setRecords(apiInfoVOList);
        return apiInfoVOPage;
    }


}




