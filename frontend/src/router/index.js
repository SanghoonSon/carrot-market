import mainRoutes from "@/router/modules/main";
import { createRouter, createWebHashHistory } from "vue-router";
import memberRoutes from "@/router/modules/member";

const routes = [
  ...mainRoutes,
  ...memberRoutes
]

export default new createRouter({
  // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
  history: createWebHashHistory(),
  routes, // short for `routes: routes`
})
