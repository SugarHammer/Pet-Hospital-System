import request from '@/utils/request'

export function queryAllPet() {
  return request({
    url: '/archives/findAll/noPage',
    method: 'post',
    data: {}
  })
}
