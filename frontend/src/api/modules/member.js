import http from "@/http";

const BASE_URL = '/api/v1/members'

const MemberService = {
  get() {
    return http.get(`${BASE_URL}/me`)
  },
  put(memberInfo) {
    return http.put(`${BASE_URL}/me`, {
      name: memberInfo.name,
      address: memberInfo.address,
    })
  },
  changePassword(memberInfo) {
    return http.patch(`${BASE_URL}/me/password`, {
      id: memberInfo.id,
      password: memberInfo.password
    })
  }
}

export default MemberService
