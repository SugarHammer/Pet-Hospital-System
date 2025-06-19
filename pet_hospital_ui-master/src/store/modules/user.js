import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken, setUser } from '@/utils/auth'
import router, { resetRouter } from '@/router'
import Layout from '@/layout'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_USER: (state, user) => {
    state.user = user
  }
}

const allResources = {
  '@/layout': () => import('@/layout'),
  '@/views/appointment/manage': () => import('@/views/appointment/manage'),
  '@/views/pet/archives': () => import('@/views/pet/archives'),
  '@/views/daily/health': () => import('@/views/daily/health'),
  '@/views/appointment/appointment': () => import('@/views/appointment/appointment'),
  '@/views/hospital/guide': () => import('@/views/hospital/guide'),
  '@/views/hospital/notice': () => import('@/views/hospital/notice'),
  '@/views/hospital/guide_notice': () => import('@/views/hospital/guide_notice'),
  '@/views/hospital/doctor': () => import('@/views/hospital/doctor'),
  '@/views/system/user': () => import('@/views/system/user'),
  '@/views/system/role': () => import('@/views/system/role'),
  '@/views/system/personal': () => import('@/views/system/personal'),
  '@/views/hospital/form': () => import('@/views/hospital/form'),
  '@/views/hospital/medicine': () => import('@/views/hospital/medicine'),
  '@/views/hospital/medicine/PetMedicine': () => import('@/views/hospital/medicine/PetMedicine')
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      login(userInfo).then(response => {
        commit('SET_TOKEN', response.body.data)
        setToken(response.body.data)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response.body

        if (!data) {
          return reject('验证失败，请重新登录')
        }
        resetRouter()
        const menus = getMenusFromResources(data.resourceDTOList)
        console.log(menus)
        for (let i = 0; i < menus.length; i++) {
          router.options.routes.push(menus[i])
          router.addRoutes([menus[i]])
        }
        const { name } = data
        commit('SET_NAME', name)
        commit('SET_USER', data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

function getMenusFromResources(resources) {
  const menus = []
  menus.push({
    path: '/',
    component: Layout,
    name: 'dashboard',
    redirect: '/home',
    meta: { title: '主页', icon: 'el-icon-s-home' },
    children: [{
      path: 'home',
      name: 'home',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '主页', icon: 'el-icon-s-home' }
    }]
  })
  for (let i = 0; i < resources.length; i++) {
    if (resources[i].type === 1) {
      menus.push(getResourceMenu(resources[i]))
    }
  }
  menus.push({ path: '*', redirect: '/404', hidden: true })
  return menus
}

// 获取单个资源对应的菜单
function getResourceMenu(resource) {
  if (!resource.children) {
    return {
      path: resource.url,
      component: allResources[resource.src],
      name: resource.name,
      meta: { title: resource.name, icon: resource.icon }
    }
  }
  const menu = {
    path: resource.url,
    component: allResources[resource.src],
    name: resource.name,
    meta: { title: resource.name, icon: resource.icon },
    children: []
  }
  for (let i = 0; i < resource.children.length; i++) {
    const temp = getResourceMenu(resource.children[i])
    menu.children.push(temp)
  }
  if (menu.children.length === 0) {
    delete menu.children
    menu.component = allResources[resource.src]
  }
  return menu
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

