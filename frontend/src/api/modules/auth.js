import http from "@/http";

const BASE_URL = '/api/v1/auth'

const AuthService = {
  login(memberInfo) {
    return http.post(`${BASE_URL}/login`, memberInfo)
  },
  join(memberInfo) {
    return http.post(`${BASE_URL}/join`, memberInfo)
  }
}

export default AuthService
