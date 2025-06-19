import request from '@/utils/request'

/**
 * 发布指南
 * @param guide
 * @returns {AxiosPromise}
 */
export function addguide(guide) {
  return request({
    url: '/guide/addGuide',
    method: 'post',
    data: guide
  })
}

/**
 * 获取数据，列表展示
 * @param GuideQuery
 * @returns {AxiosPromise}
 */
export function selectByGuideQuery(GuideQuery) {
  return request({
    url: '/guide/selectByGuideQuery',
    method: 'get',
    params: GuideQuery
  })
}

/**
 * 删除
 * @param id
 * @returns {AxiosPromise}
 */
export function deleteguide(id) {
  return request({
    url: '/guide/deleteGuide',
    method: 'delete',
    params: { id }
  })
}

/**
 * 编辑
 * @param id
 * @returns {AxiosPromise}
 */
export function selectByid(id) {
  return request({
    url: '/guide/selectByid',
    method: 'get',
    params: { id }
  })
}

export function updateguide(guideDTO) {
  console.log(guideDTO)
  return request({
    url: '/guide/updateNotice',
    method: 'post',
    data: guideDTO
  })
}
