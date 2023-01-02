import LoginPage from "@/views/auth/LoginPage";
import MemberJoinPage from "@/views/auth/JoinPage";

const authRoutes = [
  {
    path: '/join',
    component: MemberJoinPage
  },
  {
    path: '/login',
    component: LoginPage
  }
]
export default authRoutes
