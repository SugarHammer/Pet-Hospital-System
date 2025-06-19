import request from '@/utils/request'

/**
 * 查询全部宠物档案
 * @param petQuery
 * @returns {AxiosPromise}
 */
export function queryPetQuery(petQuery) {
  return request({
    url: '/archives/findAll',
    method: 'post',
    data: petQuery
  })
}

/**
 * 增加宠物档案
 * @param pet
 * @returns {*}
 */
export function addPet(pet) {
  return request({
    url: '/archives/add',
    method: 'post',
    data: pet
  })
}

/**
 * 通过id查询宠物档案
 * @param id
 * @returns {AxiosPromise}
 */
export function queryPetById(id) {
  return request({
    url: '/archives/findPet',
    method: 'get',
    params: {id}
  })
}

/**
 * 更新宠物档案
 * @param pet
 * @returns {AxiosPromise}
 */
export function updatePet(pet) {
  return request({
    url: '/archives/updatePet',
    method: 'post',
    data: pet
  })
}

/**
 * 删除宠物档案
 * @param id
 * @returns {AxiosPromise}
 */
export function deletePet(id) {
  return request({
    url: '/archives/deletePet',
    method: 'post',
    params: {id}
  })
}

/**
 * 通过宠物id查询宠物病例列表
 * @param id
 * @returns {AxiosPromise}
 */
export function queryCase(id) {
  return request({
    url: '/caseRecord/getById',
    method: 'get',
    params: {id}
  })
}

/**
 * 增加病例
 * @param caseRecord
 * @returns {AxiosPromise}
 */
export function addCaseRecord(caseRecord) {
  return request({
    url: '/caseRecord/add',
    method: 'post',
    data: caseRecord
  })
}

/**
 * 删除宠物病例
 * @param id
 * @returns {AxiosPromise}
 */
export function deleteCase(id) {
  return request({
    url: '/caseRecord/delete',
    method: 'post',
    params: {id}
  })
}

/**
 * 通过id查询宠物病例
 * @param id
 * @returns {AxiosPromise}
 */
export function queryCaseById(id) {
  return request({
    url: '/caseRecord/getByCaseId',
    method: 'get',
    params: {id}
  })
}

/**
 * 更新宠物病例
 * @param id
 * @returns {AxiosPromise}
 */
export function updateCase(caseRecord) {
  return request({
    url: '/caseRecord/update',
    method: 'post',
    data: caseRecord
  })
}

/**
 * 查询全部宠物档案-无分页
 * @param petQuery
 * @returns {AxiosPromise}
 */
export function queryPetQueryAll(petQuery) {
  return request({
    url: '/archives/findAll/noPage',
    method: 'post',
    data: petQuery
  })
}
