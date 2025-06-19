import request from '@/utils/request'

export function queryHealth(healthQuery) {
  return request({
    url: '/health',
    method: 'get',
    params: healthQuery
  })
}

export function addHealth(healthDTO) {
  return request({
    url: '/health',
    method: 'post',
    data: healthDTO
  })
}

export function updateHealth(healthDTO) {
  return request({
    url: '/health',
    method: 'put',
    data: healthDTO
  })
}

export function deleteHealth(healthDTO) {
  return request({
    url: '/health',
    method: 'delete',
    data: healthDTO
  })
}

