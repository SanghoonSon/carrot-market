<template>
  <v-app-bar flat>
    <v-container class="fill-height d-flex align-center">
      <v-btn
          v-for="item in items"
          :key="item"
          :to="item.to"
          variant="text"
      >
        {{ item.title }}
      </v-btn>

      <v-spacer></v-spacer>

      <v-responsive max-width="260">
        <v-text-field
            density="compact"
            variant="solo"
            autocomplete="off"
        ></v-text-field>
      </v-responsive>

      <LogInButton v-if="!loggedIn"/>
      <MyPageButton v-if="loggedIn" />
      <LogoutButton v-if="loggedIn" />
    </v-container>
  </v-app-bar>
</template>

<script>
import LogInButton from "@/views/base/header/components/LoginButton";
import { mapGetters } from "vuex";
import MyPageButton from "@/views/base/header/components/ProfileButton";
import LogoutButton from "@/views/base/header/components/LogoutButton";

export default {
  components: {LogoutButton, MyPageButton, LogInButton},
  computed: {
    ...mapGetters(['loggedIn'])
  },
  data: () => ({
    items: [
      {
        title: '당근마켓',
        to: '/',
      },
      {
        title: '중고거래',
        to: '/',
      },
      {
        title: '동네가게',
        to: '/',
      },
      {
        title: '알바',
        to: '/',
      },
      {
        title: '부동산 직거래',
        to: '/',
      },
    ],
  })
}
</script>
