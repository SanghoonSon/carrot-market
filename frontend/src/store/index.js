import { createStore } from "vuex";
import auth from "@/store/modules/auth";
import member from "@/store/modules/member";
import snackbar from "@/store/modules/snackbar";

export default new createStore({
  modules: {
    auth,
    member,
    snackbar
  }
})
