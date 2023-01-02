import mainRoutes from "@/router/modules/main";
import { createRouter, createWebHistory } from "vue-router";
import memberRoutes from "@/router/modules/member";
import authRoutes from "@/router/modules/auth";

const routes = [
  ...mainRoutes,
  ...authRoutes,
  ...memberRoutes
]

export default new createRouter({
  // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
  history: createWebHistory(),
  routes, // short for `routes: routes`
})
