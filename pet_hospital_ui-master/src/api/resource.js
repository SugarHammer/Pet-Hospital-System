import request from '@/utils/request'

export function queryResource(resourceQuery) {
  return request({
    url: '/resource',
    method: 'get',
    params: resourceQuery
  })
}
