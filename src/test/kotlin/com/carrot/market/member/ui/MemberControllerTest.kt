package com.carrot.market.member.ui

import com.carrot.market.base.ControllerTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(MemberController::class)
class MemberControllerTest: ControllerTest() {

    @Test
    @DisplayName("회원가입 화면 요청에 응답으로 회원가입 폼을 반환한다")
    fun `requestMemberJoinViewThenReturnRegisterForm `() {
        mockMvc.perform(get("/member/join")
            .accept(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk)
            .andExpect(view().name("member/join"))
    }
}
