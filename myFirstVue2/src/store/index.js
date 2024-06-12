// store/index.js
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    username: null // 存储用户信息
  },
  mutations: {
    setUser(state, username) {
      state.username = username; // 更新用户信息
    }
  },
  actions: {
    // 在这里定义触发 mutations 的动作
    initializeStore({ commit }) {
      // 从本地存储中获取用户信息并保存到 Vuex 中
      const user = JSON.parse(localStorage.getItem('user'));
      if (user) {
        commit('setUser', user);
      }
    }
  }
});
