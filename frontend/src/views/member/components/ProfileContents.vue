<template>
  <v-sheet
      min-height="70vh"
      rounded="lg"
  >
    <section id="controls">
      <v-container bg fill-height grid-list-md text-xs-center>
        <v-card
            class="mx-auto pa-12 pt-8 pb-8"
            elevation="8"
            rounded="lg"
            max-width="500"
        >
          <v-form ref="editProfileForm" v-model="validProfile" @submit.prevent>
            <div class="text-subtitle-1 text-medium-emphasis">이메일</div>
            <v-text-field
                v-model="member.email"
                :rules="rules.member.email"
                type="email"
                density="compact"
                placeholder="test@test.com"
                variant="plain"
                readonly
            ></v-text-field>

            <div class="text-subtitle-1 text-medium-emphasis">이름</div>
            <v-text-field
                v-model="member.name"
                :rules="rules.member.name"
                autocomplete="off"
                density="compact"
                placeholder="홍길동"
                variant="outlined"
            ></v-text-field>

            <div class="text-subtitle-1 text-medium-emphasis">우편번호</div>
            <v-text-field
                v-model="member.address.zipcode"
                append-inner-icon="mdi-magnify"
                density="compact"
                variant="underlined"
                readonly
                @click:append-inner="onSearchAddress"
            ></v-text-field>

            <div class="text-subtitle-1 text-medium-emphasis">주소</div>
            <v-text-field
                v-model="member.address.address"
                density="compact"
                variant="underlined"
                readonly
            ></v-text-field>

            <div class="text-subtitle-1 text-medium-emphasis">상세주소</div>
            <v-text-field
                v-model="member.address.details"
                density="compact"
                variant="outlined"
            ></v-text-field>

            <v-spacer></v-spacer>

            <v-btn
                color="success"
                class="mr-4"
                @click.prevent="onEditMember"
                :disabled="!validProfile"
            >
              내정보 수정
            </v-btn>
          </v-form>
        </v-card>

        <v-spacer class="mx-auto pa-12 pt-8 pb-8"></v-spacer>

        <v-card
            class="mx-auto pa-12 pt-8 pb-8"
            elevation="8"
            rounded="lg"
            max-width="500"
        >
          <v-form ref="editPasswordForm" v-model="validPassword" @submit.prevent>
            <div class="text-subtitle-1 text-medium-emphasis">패스워드</div>
            <v-text-field
                v-model="fields.password"
                :rules="rules.member.password"
                type="password"
                density="compact"
                placeholder="enter your new password"
                variant="outlined"
                autocomplete="off"
            ></v-text-field>

            <div class="text-subtitle-1 text-medium-emphasis">패스워드 확인</div>
            <v-text-field
                v-model="fields.passwordConfirm"
                :rules="[(fields.password && fields.password === fields.passwordConfirm) || '비밀번호가 일치하지 않습니다.']"
                type="password"
                density="compact"
                placeholder="enter your new password"
                variant="outlined"
                autocomplete="off"
            ></v-text-field>
          </v-form>

          <v-spacer></v-spacer>

          <v-btn
              color="primary"
              class="mr-4"
              @click.prevent="onChangePassword"
              :disabled="!validPassword"
          >
            패스워드 변경
          </v-btn>
        </v-card>
      </v-container>
    </section>
  </v-sheet>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from "vuex";
import validator from "@/utils/validator";
import { CHANGE_PASSWORD, FETCH_MEMBER, PUT_MEMBER } from "@/store/shared/actionTypes";
import { SNACKBAR_MESSAGES } from "@/utils/constants";
import { SHOW_SNACKBAR } from "@/store/shared/mutationTypes";

export default {
  name: 'ProfileContents',
  computed: {
    ...mapGetters(['member'])
  },
  data: () => ({
    fields: {
      password: null,
      passwordConfirm: null
    },
    validProfile: false,
    validPassword: false,
    rules: {...validator},
  }),
  mounted() {
    return this.$refs.editProfileForm.validate()
  },
  methods: {
    ...mapMutations([SHOW_SNACKBAR]),
    ...mapActions([PUT_MEMBER, FETCH_MEMBER, CHANGE_PASSWORD]),
    isValidProfileForm() {
      return this.$refs.editProfileForm.validate()
    },
    isValidPasswordForm() {
      return this.$refs.editPasswordForm.validate()
    },
    onSearchAddress() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          // 우편번호를 입력한다.
          var addressField = this.member.address;
          addressField.zipcode = data.zonecode;

          if (addressField.details !== "") {
            addressField.details = "";
          }
          if (data.userSelectedType === "R") {
            // 사용자가 도로명 주소를 선택했을 경우
            addressField.address = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            addressField.address = data.jibunAddress;
          }

          if (data.buildingName !== "" && data.apartment === "Y") {
            addressField.address += addressField.address !== "" ? `, ${data.buildingName}` : data.buildingName;
          }
        },
      }).open();
    },
    async onEditMember() {
      if (!this.isValidProfileForm()) {
        return
      }

      try {
        await this.putMember({...this.member})
        await this.fetchMember()
        this.showSnackbar(SNACKBAR_MESSAGES.COMMON.SUCCESS)
      } catch (e) {
        this.showSnackbar(SNACKBAR_MESSAGES.COMMON.FAIL)
        console.error(e)
      }
    },
    async onChangePassword() {
      if(!this.isValidPasswordForm()) {
        this.showSnackbar(SNACKBAR_MESSAGES.MEMBER.NOT_MATCH_PASSWORD)
        return;
      }

      try {
        await this.changePassword({
          id: this.member.id,
          password: this.fields.password
        })
        this.showSnackbar(SNACKBAR_MESSAGES.COMMON.SUCCESS)
      } catch (e) {
        this.showSnackbar(SNACKBAR_MESSAGES.COMMON.FAIL)
        console.error(e)
      }
    }
  }
}
</script>
