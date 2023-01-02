<template>
  <MenuListItem>
    <v-btn to="/" @click="logout">로그아웃</v-btn>
  </MenuListItem>
</template>

<script>
import MenuListItem from '@/components/menus/MenuListItem'
import { SET_ACCESS_TOKEN, SET_MEMBER, SHOW_SNACKBAR } from '@/store/shared/mutationTypes'
import { mapActions, mapMutations } from 'vuex'
import { SNACKBAR_MESSAGES } from '@/utils/constants'
import { LOGOUT } from "@/store/shared/actionTypes";

export default {
  name: 'LogoutButton',
  components: { MenuListItem },
  methods: {
    ...mapActions([LOGOUT]),
  }
}
</script>
