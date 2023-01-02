package com.carrot.market.auth.ui

import com.carrot.market.auth.application.AuthenticateService
import com.carrot.market.auth.application.JoinMemberRequest
import com.carrot.market.auth.application.LoginRequest
import com.carrot.market.auth.application.TokenResponse
import com.carrot.market.base.RestControllerTest
import com.carrot.market.member.application.MemberResponse
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.post

private const val BASE_URL = "/api/v1/auth"

@WebMvcTest(AuthController::class)
class AuthControllerTest: RestControllerTest() {
    @MockkBean
    private lateinit var authenticateService: AuthenticateService

    @Test
    @DisplayName("회원 가입 요청시 가입 후 생성 된 회원 아이디를 반환한다")
    fun requestMemberJoinThenReturnCreateResponse() {
        // given
        val joinMemberRequest = JoinMemberRequest("앤디", "andy@test.com", "1234")
        val memberResponse = MemberResponse(1L, "앤디", "andy@test.com")
        every { authenticateService.joinMember(joinMemberRequest) } returns memberResponse

        // when & then
        mockMvc.post("${BASE_URL}/join") {
            jsonContent(joinMemberRequest)
        }.andExpect {
            status { isCreated() }
            header { location("/api/v1/members/${memberResponse.id}") }
        }
    }

    @Test
    @DisplayName("로그인 요청시 회원 인증 후 토큰 정보를 반환한다")
    fun requestMemberLoginThenReturnTokenResponse() {
        // given
        val loginRequest = LoginRequest("andy@test.com", "1234")
        val tokenResponse = TokenResponse("accessToken")
        every { authenticateService.loginMember(loginRequest) } returns tokenResponse

        // when & then
        mockMvc.post("${BASE_URL}/login") {
            jsonContent(loginRequest)
        }.andExpect {
            status { isOk() }
            content { success(tokenResponse) }
        }
    }
}
