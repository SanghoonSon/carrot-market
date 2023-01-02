import ProfilePage from "@/views/member/MyPage";
import ProfileContents from "@/views/member/components/ProfileContents";
import MyHistoryContents from "@/views/member/components/MyHistoryContents";

const memberRoutes = [
  {
    name: 'member',
    path: '/member',
    component: ProfilePage,
    children: [
      {path: "me", component: ProfileContents},
      {path: "me/secret", component: MyHistoryContents},
      {path: "me/history", component: MyHistoryContents},
    ]
  }
]
export default memberRoutes
