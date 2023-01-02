import { SET_ACCESS_TOKEN } from "@/store/shared/mutationTypes";
import { CLEAR_MEMBER, FETCH_MEMBER, JOIN_MEMBER, LOGIN, LOGOUT } from "@/store/shared/actionTypes";
import AuthService from "@/api/modules/auth";

const state = {
  accessToken: null,
  loggedIn: false
}

const getters = {
  accessToken(state) {
    return state.accessToken
  },
  loggedIn(state) {
    return state.loggedIn
  }
}

const mutations = {
  [SET_ACCESS_TOKEN](state, accessToken) {
    state.accessToken = accessToken
    state.loggedIn = accessToken !== ''
  }
}

const actions = {
  async [LOGIN]({ commit, dispatch }, loginInfo) {
    return AuthService.login(loginInfo).then(({ data }) => {
      commit(SET_ACCESS_TOKEN, data.body.accessToken)
      localStorage.setItem('token', data.body.accessToken)
      dispatch(FETCH_MEMBER)
    })
  },
  async [LOGOUT]({ commit, dispatch }) {
    commit(SET_ACCESS_TOKEN, '')
    localStorage.removeItem("token")
    dispatch(CLEAR_MEMBER)
  },
  async [JOIN_MEMBER]({ commit }, memberInfo) {
    return AuthService.join(memberInfo);
  },
}

export default {
  state,
  getters,
  actions,
  mutations
}
