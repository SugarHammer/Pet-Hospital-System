import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function queryUser(userQuery) {
  return request({
    url: '/user',
    method: 'get',
    params: userQuery
  })
}

export function updateUser(userDTO) {
  return request({
    url: '/user',
    method: 'put',
    data: userDTO
  })
}

export function updatePersonalInfo(userDTO) {
  return request({
    url: '/user/updatePersonalInfo',
    method: 'put',
    data: userDTO
  })
}

export function updatePassword(updatePasswordDTO) {
  return request({
    url: '/user/updatePassword',
    method: 'put',
    data: updatePasswordDTO
  })
}
