<template>
  <v-container>
    <v-row>
      <v-col cols="3">
        <MyPageSidebar />
      </v-col>

      <v-col cols="7">
        <router-view />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

import MyPageSidebar from "@/views/member/components/MyPageSidebar";

export default {
  name: 'MyPage',
  components: {MyPageSidebar},
  data: () => ({

  })
}
</script>
