import request from '@/utils/request'

export function queryAllRole() {
  return request({
    url: '/role',
    method: 'get'
  })
}

export function updateRole(role) {
  return request({
    url: '/role',
    method: 'put',
    data: role
  })
}

export function addRole(role) {
  return request({
    url: '/role',
    method: 'post',
    data: role
  })
}

export function deleteRole(role) {
  return request({
    url: '/role',
    method: 'delete',
    data: role
  })
}
