import request from '@/utils/request'

// 请假表单管理
export default {

  // 新增申请
  add(data) {
    return request({
      url: '/leave',
      method: 'post',
      data
    })
  },

  // 申请列表
  getList(query) {
    return request({
      url: '/leave/list',
      method: 'get',
      params: query
    })
  },

  // 查询详情
  getById(id) {
    return request({
      url: `/leave/${id}`,
      method: 'get'
    })
  },

  // 修改请假申请
  update(data) {
    return request({
      url: '/leave',
      method: 'put',
      data
    })
  },

}
