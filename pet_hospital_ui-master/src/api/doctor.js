import request from '@/utils/request'

export function queryDoctor(doctorQuery) {
  return request({
    url: '/doctor',
    method: 'get',
    params: doctorQuery
  })
}

export function addDoctor(doctorDTO) {
  return request({
    url: '/doctor',
    method: 'post',
    data: doctorDTO
  })
}

export function updateDoctor(doctorDTO) {
  return request({
    url: '/doctor',
    method: 'put',
    data: doctorDTO
  })
}

export function deleteDoctor(doctorDTO) {
  return request({
    url: '/doctor',
    method: 'delete',
    data: doctorDTO
  })
}

