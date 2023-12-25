// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** addApiInfo POST /api/apiInfo/add */
export async function addApiInfoUsingPost(
  body: API.ApiInfoAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLong_>('/api/apiInfo/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteApiInfo POST /api/apiInfo/delete */
export async function deleteApiInfoUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/apiInfo/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** editApiInfo POST /api/apiInfo/edit */
export async function editApiInfoUsingPost(
  body: API.ApiInfoEditRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/apiInfo/edit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getApiInfoVOById GET /api/apiInfo/get/vo */
export async function getApiInfoVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiInfoVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseApiInfoVO_>('/api/apiInfo/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listApiInfoVOByPage POST /api/apiInfo/list/page/vo */
export async function listApiInfoVoByPageUsingPost(
  body: API.ApiInfoQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageApiInfoVO_>('/api/apiInfo/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listMyApiInfoVOByPage POST /api/apiInfo/my/list/page/vo */
export async function listMyApiInfoVoByPageUsingPost(
  body: API.ApiInfoQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageApiInfoVO_>('/api/apiInfo/my/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** searchApiInfoVOByPage POST /api/apiInfo/search/page/vo */
export async function searchApiInfoVoByPageUsingPost(
  body: API.ApiInfoQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageApiInfoVO_>('/api/apiInfo/search/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** updateApiInfo POST /api/apiInfo/update */
export async function updateApiInfoUsingPost(
  body: API.ApiInfoUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/apiInfo/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
