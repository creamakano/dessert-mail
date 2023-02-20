import { createStore } from 'vuex'
/**
 * 创建仓库和导出
 */
const store = createStore({
  state: {
    num: 111,
    userInfo: {
      id: "",
      name: "",
      creationTime: "",
    }
  },
  mutations: {
    setUserInfo (state, payload) {
      state.userInfo = payload;
    },
    setNum (state, payload) {
      state.num = payload
    }

  }
})

export default store