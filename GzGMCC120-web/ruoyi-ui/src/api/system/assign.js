import request from '@/utils/request'

// 查询120核阳转运调派列表
export function listAssign(query) {
  return request({
    url: '/postran/assign/list',
    method: 'get',
    params: query
  })
}

// 查询120核阳转运调派详细
export function getAssign(fdId) {
  return request({
    url: '/postran/assign/' + fdId,
    method: 'get'
  })
}

// 新增120核阳转运调派
export function addAssign(data) {
  return request({
    url: '/postran/assign',
    method: 'post',
    data: data
  })
}

// 修改120核阳转运调派
export function updateAssign(data) {
  return request({
    url: '/postran/assign',
    method: 'put',
    data: data
  })
}

// 删除120核阳转运调派
export function delAssign(fdId) {
  return request({
    url: '/postran/assign/' + fdId,
    method: 'delete'
  })
}

//
export function getDictInfo(dictType) {
  return request({
    url: '/postran/dict/find?dictType=' + dictType,
    method: 'get'
  })
}



// 任务终止，连续
export function editStatus(data) {
    return request({
      url: '/postran/assign/editStatus',
      method: 'post',
      data: data
    })
}

