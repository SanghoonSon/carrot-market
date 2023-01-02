import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import auth from "@/store/modules/auth";
import member from "@/store/modules/member";
import snackbar from "@/store/modules/snackbar";

export default new createStore({
  modules: {
    auth,
    member,
    snackbar
  },
  plugins: [createPersistedState({
    paths: ["auth", "member"],
  })]
})
