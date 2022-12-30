<template>
  <v-app-bar>
    <v-container class="d-flex align-center py-0">
      <v-app-bar-title class="pl-0">
        <v-tabs>
          <v-tab
              v-for="item in items"
              :key="item"
              :to="item.to"
          >
            {{ item.title }}
          </v-tab>
        </v-tabs>
      </v-app-bar-title>

      <v-spacer />
      <LogInButton />

    </v-container>
  </v-app-bar>
</template>

<script>
import LogInButton from "@/views/base/header/components/LoginButton";
export default {
  components: {LogInButton},
  data: () => ({
    items: [
      {
        title: '당근마켓',
        to: '/',
      },
      {
        title: '중고거래',
        to: '/1',
      },
      {
        title: '동네가게',
        to: '/2',
      },
      {
        title: '알바',
        to: '/3',
      },
      {
        title: '부동산 직거래',
        to: '/4',
      },
    ],
  }),

  methods: {
    addItem (item) {
      const removed = this.items.splice(0, 1)
      this.items.push(
          ...this.more.splice(this.more.indexOf(item), 1),
      )
      this.more.push(...removed)
      this.$nextTick(() => { this.currentItem = 'tab-' + item })
    },
  },
}
</script>
