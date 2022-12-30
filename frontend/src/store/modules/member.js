import { FETCH_MEMBER } from "@/store/shared/actionTypes";
import MemberService from "@/api/modules/member";
import { SET_MEMBER } from "@/store/shared/mutationTypes";

const state = {
  member: null
}

const getters = {
  member(state) {
    return state.member
  }
}

const mutations = {
  [SET_MEMBER](state, member) {
    state.member = member
  }
}

const actions = {
  async [FETCH_MEMBER]({ commit }) {
    return MemberService.get().then(({ data }) => {
      commit(SET_MEMBER, data)
    })
  }
}

export default {
  state,
  getters,
  mutations,
  actions
}
