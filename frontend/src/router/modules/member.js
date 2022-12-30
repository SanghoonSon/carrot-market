import LoginPage from "@/views/auth/LoginPage";
import MemberJoinPage from "@/views/auth/JoinPage";

const memberRoutes = [
  {
    path: '/join',
    component: MemberJoinPage
  },
  {
    path: '/login',
    component: LoginPage
  }
]
export default memberRoutes
