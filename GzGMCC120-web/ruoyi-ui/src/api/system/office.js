import request from '@/utils/request'

// 查询合同文件列表
export function listOffice(query) {
  return request({
    url: '/system/office/list',
    method: 'get',
    params: query
  })
}

// 查询合同文件详细
export function getOffice(id) {
  return request({
    url: '/system/office/' + id,
    method: 'get'
  })
}

// 新增合同文件
export function addOffice(data) {
  return request({
    url: '/system/office',
    method: 'post',
    data: data
  })
}

// 修改合同文件
export function updateOffice(data) {
  return request({
    url: '/system/office',
    method: 'put',
    data: data
  })
}

// 删除合同文件
export function delOffice(id) {
  return request({
    url: '/system/office/' + id,
    method: 'delete'
  })
}
