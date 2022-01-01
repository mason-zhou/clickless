import request from '@/utils/request'

// 查询商品数据列表
export function listProduct(query) {
  return request({
    url: '/sample/product/list',
    method: 'get',
    params: query
  })
}

// 查询商品数据详细
export function getProduct(id) {
  return request({
    url: '/sample/product/' + id,
    method: 'get'
  })
}

// 新增商品数据
export function addProduct(data) {
  return request({
    url: '/sample/product',
    method: 'post',
    data: data
  })
}

// 修改商品数据
export function updateProduct(data) {
  return request({
    url: '/sample/product',
    method: 'put',
    data: data
  })
}

// 删除商品数据
export function delProduct(id) {
  return request({
    url: '/sample/product/' + id,
    method: 'delete'
  })
}

// 导出商品数据
export function exportProduct(query) {
  return request({
    url: '/sample/product/export',
    method: 'get',
    params: query
  })
}

// 查询表头筛选项-商品数据
export function getProductFieldEnums(query) {
  return request({
    url: '/sample/product/field-enums',
    method: 'get',
    params: query
  })
}