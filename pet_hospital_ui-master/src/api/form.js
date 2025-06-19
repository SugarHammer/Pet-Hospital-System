import request from '@/utils/request'

export function petPercentage(){
  return request({
    url: '/form/petPercentage',
    method: 'get'
  })
}

export function appointmentCount() {
  return request({
    url: '/form/appointmentCount',
    method: 'get'
  })
}

export function healthCount() {
  return request({
    url: '/form/healthCount',
    method: 'get'
  })
}
