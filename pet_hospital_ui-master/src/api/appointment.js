import request from '@/utils/request'

export function queryAppointment(appointmentQuery) {
  return request({
    url: '/appointment',
    method: 'get',
    params: appointmentQuery
  })
}

export function addAppointment(appointmentDTO) {
  return request({
    url: '/appointment',
    method: 'post',
    data: appointmentDTO
  })
}

export function updateAppointment(appointmentDTO) {
  return request({
    url: '/appointment',
    method: 'put',
    data: appointmentDTO
  })
}
