import request from '@/utils/request'

// 查询模型字段
export function metaFields(query) {
  return request({
    // url: '/model/meta/fields',
    url: '/system/page-model',
    method: 'get',
    params: query
  })
}

// 查询用户选择字段
export function getUserFields(query) {
  return request({
    url: '/system/page-model/user-fields',
    method: 'get',
    params: query
  })
}

// 保存用户选择字段
export function setUserFields(data) {
  return request({
    url: '/system/page-model/user-fields',
    method: 'post',
    data: data
  })
}

// 字段数据筛选器接口  --废弃
export function fieldFilteredData(data) {
  return request({
    url: '/model/data/fieldFilteredData',
    method: 'post',
    data: data
  })
}

// 字段数据筛选器接口
export function fieldData(data) {
  return request({
    url: '/model/data/fieldData',
    method: 'post',
    data: data
  })
}

// 获取模型数据接口
export function getData(data) {
  return request({
    url: '/model/user/data',
    method: 'post',
    data: data
  })
}

// 通用导出接口
export const exportFile = (data) => {
  const filter = {
    'op': 'AND',
    'filterConditions': [],
    'filterGroups': data.headerFilterData || []
  }

  const json = {
    modelCode: data.modelCode, 	// 是	string	模型编码
    filterGroup: data.filterGroup,	// 否	object	过滤器组
    searchValue: data.searchValue,	// 否	string	模糊搜索值
    // pageNum:1,	    //否	int	分页起始页
    pageSize: -1, // 否	int	分页大小
    orderBy: data.sort && data.sort[0] ? data.sort[0]['prop'] : null, // 否	string	排序字段
    sort: data.sort && data.sort[0] ? data.sort[0]['order'] : null // 否	string	升序降序 asc或者desc
  }

  return request({
    url: '/model/user/data/export',
    method: 'post',
    data: json
  })
}
