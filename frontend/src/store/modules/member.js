import { CHANGE_PASSWORD, CLEAR_MEMBER, FETCH_MEMBER, PUT_MEMBER } from "@/store/shared/actionTypes";
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
      commit(SET_MEMBER, data.body)
    })
  },
  async [CLEAR_MEMBER]({ commit }) {
    commit(SET_MEMBER, null)
  },
  async [PUT_MEMBER]({commit, dispatch}, memberInfo) {
    return MemberService.put(memberInfo).then(({res}) => {
      dispatch(FETCH_MEMBER)
    })
  },
  async [CHANGE_PASSWORD]({commit}, memberInfo) {
    return MemberService.changePassword(memberInfo)
  }
}

export default {
  state,
  getters,
  mutations,
  actions
}
