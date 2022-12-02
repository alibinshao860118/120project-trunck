import request from '@/utils/request'

// 查询菜单列表
export function listMenu(query) {
  return request({
    url: '/system/menu/list',
    method: 'get',
    params: query
  })
}

// 查询菜单详细
export function getMenu(menuId) {
  return request({
    url: '/system/menu/' + menuId,
    method: 'get'
  })
}

// 查询菜单下拉树结构
export function treeselect() {
  return request({
    url: '/system/menu/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询菜单下拉树结构
export function roleMenuTreeselect(roleId) {
  return request({
    url: '/system/menu/roleMenuTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增菜单
export function addMenu(data) {
  return request({
    url: '/system/menu',
    method: 'post',
    data: data
  })
}

// 修改菜单
export function updateMenu(data) {
  return request({
    url: '/system/menu',
    method: 'put',
    data: data
  })
}

// 删除菜单
export function delMenu(menuId) {
  return request({
    url: '/system/menu/' + menuId,
    method: 'delete'
  })
}

// 获取字典列表数据
export function getNewData(data) {
  return request({
    url: '/postran/dict/list' + data,
    method: 'get',
  })
}
// 删除字典列表数据
export function deleteNewData(data) {
  return request({
    url: '/postran/dict/delete',
    method: 'post',
    data: data
  })
}
// 新增字典列表数据
export function addNewData(data) {
  return request({
    url: '/postran/dict/',
    method: 'post',
    data: data
  })
}
// 编辑字典列表数据
export function editNewData(data) {
  return request({
    url: '/postran/dict/',
    method: 'put',
    data: data
  })
}

// 编辑录入数据
export function editEnterData(data) {
  return request({
    url: '/postran/entry',
    method: 'put',
    data: data
  })
}
// 获取录入数据列表 
export function getEnterData(data) {
  return request({
    url: '/postran/entry/list' + data,
    method: 'get',
  })
}
// 获取录入数据列表 
export function addEnterData(data) {
  return request({
    url: '/postran/entry',
    method: 'post',
    data: data,
  })
}
// 录入页面获取字典下拉框和多选框
export function getArray(data) {
  return request({
    url: '/postran/dict/find' + data,
    method: 'get',
  })
}