import axios from 'axios'
import store from "@/store";

function init() {
  let http = axios.create({
    // baseURL: '[베이스 URL를 입력해주세요]', // 스프링 개발용
    withCredentials: true, // send cookies when cross-domain requests
    timeout: 3000, // request timeout
    headers: {
      "Content-Type": "application/json",
    },
  });

  http.interceptors.request.use(config => {
    if (!config.headers) return config;
    if (config.url === '/api/v1/auth/join') {
      return config;
    }

    let accessToken = localStorage.getItem('token');
    if (accessToken !== null) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
      config.headers["cache-control"] = "no-cache";
    }
    return config;
    }
  );

  return http
}

// create an axios instance
const http = init()

export default http
