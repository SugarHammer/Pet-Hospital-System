import request from '@/utils/request'

export function getAll(query) {
  return request({
    url: '/medicine/getAll',
    method: 'post',
    data: query
  })
}

export function getMedicine(query) {
  return request({
    url: '/medicine/getMedicine',
    method: 'post',
    data: query
  })
}

export function enterMedicine(query) {
  return request({
    url: '/medicine/enterMedicine',
    method: 'post',
    data: query
  })
}

export function addMedicine(query) {
  return request({
    url: '/medicine/add',
    method: 'post',
    data: query
  })
}

export function getAllPetMedicine(query) {
  return request({
    url: '/medicine/petMedicine/getAll',
    method: 'post',
    data: query
  })
}
