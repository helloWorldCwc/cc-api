package com.cc.ccapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.ccapi.annotation.AuthCheck;
import com.cc.ccapi.common.BaseResponse;
import com.cc.ccapi.common.DeleteRequest;
import com.cc.ccapi.common.ErrorCode;
import com.cc.ccapi.common.ResultUtils;
import com.cc.ccapi.constant.UserConstant;
import com.cc.ccapi.exception.BusinessException;
import com.cc.ccapi.exception.ThrowUtils;
import com.cc.ccapi.model.dto.apiInfo.ApiInfoAddRequest;
import com.cc.ccapi.model.dto.apiInfo.ApiInfoEditRequest;
import com.cc.ccapi.model.dto.apiInfo.ApiInfoQueryRequest;
import com.cc.ccapi.model.dto.apiInfo.ApiInfoUpdateRequest;
import com.cc.ccapi.model.entity.ApiInfo;
import com.cc.ccapi.model.entity.User;
import com.cc.ccapi.model.vo.ApiInfoVO;
import com.cc.ccapi.service.ApiInfoService;
import com.cc.ccapi.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
/**
 * 帖子接口
 *

 */
@RestController
@RequestMapping("/apiInfo")
@Slf4j
public class ApiInfoController {

    @Resource
    private ApiInfoService apiInfoService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param apiInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addApiInfo(@RequestBody ApiInfoAddRequest apiInfoAddRequest, HttpServletRequest request) {
        if (apiInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInfo apiInfo = new ApiInfo();
        BeanUtils.copyProperties(apiInfoAddRequest, apiInfo);
        apiInfoService.validApiInfo(apiInfo, true);
        User loginUser = userService.getLoginUser(request);
        apiInfo.setUserId(loginUser.getId());
        boolean result = apiInfoService.save(apiInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newApiInfoId = apiInfo.getId();
        return ResultUtils.success(newApiInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteApiInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        ApiInfo oldApiInfo = apiInfoService.getById(id);
        ThrowUtils.throwIf(oldApiInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldApiInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = apiInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param apiInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateApiInfo(@RequestBody ApiInfoUpdateRequest apiInfoUpdateRequest) {
        if (apiInfoUpdateRequest == null || apiInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInfo apiInfo = new ApiInfo();
        BeanUtils.copyProperties(apiInfoUpdateRequest, apiInfo);
        // 参数校验
        apiInfoService.validApiInfo(apiInfo, false);
        long id = apiInfoUpdateRequest.getId();
        // 判断是否存在
        ApiInfo oldApiInfo = apiInfoService.getById(id);
        ThrowUtils.throwIf(oldApiInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = apiInfoService.updateById(apiInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<ApiInfoVO> getApiInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInfo apiInfo = apiInfoService.getById(id);
        if (apiInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(apiInfoService.getApiInfoVO(apiInfo, request));
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param apiInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<ApiInfoVO>> listApiInfoVOByPage(@RequestBody ApiInfoQueryRequest apiInfoQueryRequest,
            HttpServletRequest request) {
        long current = apiInfoQueryRequest.getCurrent();
        long size = apiInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<ApiInfo> apiInfoPage = apiInfoService.page(new Page<>(current, size),
                apiInfoService.getQueryWrapper(apiInfoQueryRequest));
        return ResultUtils.success(apiInfoService.getApiInfoVOPage(apiInfoPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param apiInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<ApiInfoVO>> listMyApiInfoVOByPage(@RequestBody ApiInfoQueryRequest apiInfoQueryRequest,
            HttpServletRequest request) {
        if (apiInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        apiInfoQueryRequest.setUserId(loginUser.getId());
        long current = apiInfoQueryRequest.getCurrent();
        long size = apiInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<ApiInfo> apiInfoPage = apiInfoService.page(new Page<>(current, size),
                apiInfoService.getQueryWrapper(apiInfoQueryRequest));
        return ResultUtils.success(apiInfoService.getApiInfoVOPage(apiInfoPage, request));
    }

    // endregion

    /**
     * 分页搜索（从 ES 查询，封装类）
     *
     * @param apiInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/search/page/vo")
    public BaseResponse<Page<ApiInfoVO>> searchApiInfoVOByPage(@RequestBody ApiInfoQueryRequest apiInfoQueryRequest,
            HttpServletRequest request) {
        long size = apiInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<ApiInfo> apiInfoPage = apiInfoService.page(new Page<>(apiInfoQueryRequest.getCurrent(), apiInfoQueryRequest.getPageSize()), apiInfoService.getQueryWrapper(apiInfoQueryRequest));
        return ResultUtils.success(apiInfoService.getApiInfoVOPage(apiInfoPage, request));
    }

    /**
     * 编辑（用户）
     *
     * @param apiInfoEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editApiInfo(@RequestBody ApiInfoEditRequest apiInfoEditRequest, HttpServletRequest request) {
        if (apiInfoEditRequest == null || apiInfoEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInfo apiInfo = new ApiInfo();
        BeanUtils.copyProperties(apiInfoEditRequest, apiInfo);
        // 参数校验
        apiInfoService.validApiInfo(apiInfo, false);
        User loginUser = userService.getLoginUser(request);
        long id = apiInfoEditRequest.getId();
        // 判断是否存在
        ApiInfo oldApiInfo = apiInfoService.getById(id);
        ThrowUtils.throwIf(oldApiInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldApiInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = apiInfoService.updateById(apiInfo);
        return ResultUtils.success(result);
    }

}
