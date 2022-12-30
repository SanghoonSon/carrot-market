import http from "@/http";

const BASE_URL = '/api/v1/members'

const MemberService = {
  get() {
    return http.get(`${BASE_URL}/me`)
  }
}

export default MemberService
