import request from '@/utils/request'

// 查询到达医院导入列表
export function listImport(query) {
  return request({
    url: '/postran/import/list',
    method: 'get',
    params: query
  })
}

// 查询到达医院导入详细
export function getImport(importId) {
  return request({
    url: '/postran/import/' + importId,
    method: 'get'
  })
}

// 新增到达医院导入
export function addImport(data) {
  return request({
    url: '/postran/import',
    method: 'post',
    data: data
  })
}

// 修改到达医院导入
export function updateImport(data) {
  return request({
    url: '/postran/import',
    method: 'put',
    data: data
  })
}

// 删除到达医院导入
export function delImport(importId) {
  return request({
    url: '/postran/import/' + importId,
    method: 'delete'
  })
}
