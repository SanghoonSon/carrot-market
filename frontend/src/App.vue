<template>
  <v-app>
    <Header />
    <v-main>
      <router-view />
    </v-main>
    <Snackbar />
    <Footer />
  </v-app>
</template>

<script>
import Header from '@/views/base/header/Header'
import Footer from "@/views/base/footer/Footer";
import Snackbar from "@/components/snackbars/Snackbar";
import { mapActions } from "vuex";
import { LOGOUT, FETCH_MEMBER } from "@/store/shared/actionTypes";

export default {
  name: 'App',
  components: {
    Snackbar,
    Footer,
    Header
  },
  created() {
    const accessToken = localStorage.getItem('token')
    if (!accessToken) {
      return
    }
    this.fetchMember()
        .catch(error => {
          if(error.response.status === 401) {
            this.logout()
            this.$router.replace(`/`)
          }
        })
  },
  methods: {
    ...mapActions([FETCH_MEMBER, LOGOUT])
  }
}
</script>
