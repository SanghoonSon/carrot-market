<template>
  <v-sheet rounded="lg">
    <v-avatar
        class="mb-4"
        color="grey-darken-1"
        size="64"
    ></v-avatar>

    <div>{{ member?.email }}</div>
  </v-sheet>
  <v-divider></v-divider>
  <v-list rounded="lg">
    <v-list-item
        v-for="item in items"
        :key="item"
        :to="item.to"
    >
      <v-list-item-title>{{ item.title }}</v-list-item-title>
    </v-list-item>
  </v-list>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: 'MyPageSidebar',
  computed: {
    ...mapGetters(['member'])
  },
  data: () => ({
    items: [
      {title: '내프로필', to: "/member/me"},
      {title: '보안설정', to: "/member/me/secret"},
      {title: '이력관리', to: "/member/me/history"},
    ],
    cards: ['Today', 'Yesterday'],
    drawer: null,
    links: [
      ['mdi-inbox-arrow-down', 'Inbox'],
      ['mdi-send', 'Send'],
      ['mdi-delete', 'Trash'],
      ['mdi-alert-octagon', 'Spam'],
    ],
  })
}
</script>
