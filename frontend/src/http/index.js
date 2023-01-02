import axios from 'axios'

const not_authentication_urls = [
  '/api/v1/auth/join',
  '/api/v1/auth/login'
]

function init() {
  let http = axios.create({
    // baseURL: '[베이스 URL를 입력해주세요]', // 스프링 개발용
    withCredentials: true, // send cookies when cross-domain requests
    timeout: 5000, // request timeout
    headers: {
      "Content-Type": "application/json",
    },
  });

  http.interceptors.request.use(config => {
    if (!config.headers) return config;
    if (not_authentication_urls.includes(config.url)) {
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
