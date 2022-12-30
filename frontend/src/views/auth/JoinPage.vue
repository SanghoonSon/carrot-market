<template>
  <v-container bg fill-height grid-list-md text-xs-center>
    <v-card
        class="mx-auto pa-12 pt-8 pb-8"
        elevation="8"
        rounded="lg"
        max-width="500"
    >
      <v-form ref="memberJoinForm" v-model="valid" @submit.prevent>
        <div class="text-subtitle-1 text-medium-emphasis">이름</div>
        <v-text-field
            v-model="member.name"
            :rules="rules.member.name"
            density="compact"
            placeholder="홍길동"
            variant="outlined"
        ></v-text-field>

        <div class="text-subtitle-1 text-medium-emphasis">이메일</div>
        <v-text-field
            v-model="member.email"
            :rules="rules.member.email"
            type="email"
            density="compact"
            placeholder="test@test.com"
            variant="outlined"
        ></v-text-field>

        <div class="text-subtitle-1 text-medium-emphasis">패스워드</div>
        <v-text-field
            v-model="member.password"
            :rules="rules.member.password"
            type="password"
            density="compact"
            placeholder="Enter your password"
            variant="outlined"
        ></v-text-field>

        <div class="text-subtitle-1 text-medium-emphasis">패스워드 확인</div>
        <v-text-field
            v-model="member.confirmPassword"
            :rules="[(member.password && member.password === member.confirmPassword) || '비밀번호가 일치하지 않습니다.']"
            type="password"
            density="compact"
            placeholder="Enter your password"
            variant="outlined"
        ></v-text-field>

        <v-spacer></v-spacer>

        <v-btn
            color="success"
            class="mr-4"
            @click.prevent="onJoinMember"
            :disabled="!valid"
        >
          회원가입
        </v-btn>
      </v-form>
    </v-card>
  </v-container>
</template>

<script>
import validator from "@/utils/validator";
import { mapActions, mapMutations } from "vuex";
import { SHOW_SNACKBAR } from "@/store/shared/mutationTypes";
import { SNACKBAR_MESSAGES } from "@/utils/constants";
import { JOIN_MEMBER } from "@/store/shared/actionTypes";

export default {
  name: 'MemberJoinPage',
  methods: {
    ...mapMutations([SHOW_SNACKBAR]),
    ...mapActions([JOIN_MEMBER]),
    isValid() {
      return this.$refs.memberJoinForm.validate()
    },
    async onJoinMember() {
      if (!this.isValid()) {
        return
      }

      try {
        const { name, email, password } = this.member
        await this.joinMember({ name, email, password })
        this.showSnackbar(SNACKBAR_MESSAGES.COMMON.SUCCESS)
        await this.$router.replace(`/login`)
      } catch (e) {
        this.showSnackbar(SNACKBAR_MESSAGES.COMMON.FAIL)
      }
    }
  },
  data() {
    return {
      valid: false,
      rules: {...validator},
      member: {
        name: '',
        email: '',
        password: '',
        confirmPassword: '',
      }
    }
  }
}
</script>
