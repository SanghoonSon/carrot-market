import { createApp } from 'vue'
import App from './App.vue'
import vuetify from "@/utils/plugin/vuetify";
import router from "@/router";
import store from "@/store";

const app = createApp(App)
  .use(vuetify)
  .use(router)
  .use(store)
  .mount('#app');

