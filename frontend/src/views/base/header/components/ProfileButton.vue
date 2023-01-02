<template>
  <MenuListItem>
    <v-btn to="/member/me">프로필</v-btn>
  </MenuListItem>
</template>

<script>
import MenuListItem from '@/components/menus/MenuListItem'

export default {
  name: 'ProfileButton',
  components: {MenuListItem},
}
</script>
