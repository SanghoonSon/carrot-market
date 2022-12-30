<template>
  <MenuListItem>
    <v-btn to="/login">로그인</v-btn>
  </MenuListItem>
</template>

<script>
import MenuListItem from '@/components/menus/MenuListItem'

export default {
  name: 'LogInButton',
  components: { MenuListItem },
  methods: {
  }
}
</script>
