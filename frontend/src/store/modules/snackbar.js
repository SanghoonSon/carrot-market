import { SHOW_SNACKBAR } from "@/store/shared/mutationTypes";

const state = {
  isShow: false,
  message: ''
}

const getters = {
  isShow(state) {
    return state.isShow
  },
  message(state) {
    return state.message
  }
}

const mutations = {
  [SHOW_SNACKBAR](state, message) {
    state.isShow = !state.isShow
    state.message = message
  }
}

export default {
  state,
  getters,
  mutations
}
