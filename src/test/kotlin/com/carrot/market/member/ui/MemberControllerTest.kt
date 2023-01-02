package com.carrot.market.member.ui

import com.carrot.market.auth.infrastructure.AUTHORIZATION
import com.carrot.market.base.RestControllerTest
import com.carrot.market.base.VALID_TOKEN
import com.carrot.market.fixture.createMember
import com.carrot.market.member.application.*
import com.carrot.market.member.domain.Address
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

private const val BASE_URL = "/api/v1/members"

@WebMvcTest(MemberController::class)
internal class MemberControllerTest: RestControllerTest() {

    @MockkBean
    private lateinit var memberService: MemberService

    @Test
    @DisplayName("회원 정보 요청시 회원 인증 후 회원 정보를 반환한다")
    fun requestMemberInfoThenReturnMemberResponse() {
        // given
        val member = createMember()
        val memberResponse = MemberResponse(member)
        every { memberService.findById(any()) } returns memberResponse

        // when & then
        mockMvc.get("${BASE_URL}/me") {
            header(AUTHORIZATION, VALID_TOKEN)
        }.andExpect {
            status { isOk() }
            content { success(memberResponse) }
        }
    }

    @Test
    @DisplayName("회원 정보 수정 요청시 회원 인증 후 회원 정보를 수정한다")
    fun requestUpdateMemberInfoThenReturnOkResponse() {
        // given
        val address = Address("123-456", "경기도 성남시 분당구 건물대로132", "잘지어진 건물 113호")
        val updateMemberInfoRequest = UpdateMemberInfoRequest("수정된이름", address)
        every { memberService.updateMember(any(), any()) } just Runs

        // when & then
        mockMvc.put("${BASE_URL}/me") {
            header(AUTHORIZATION, VALID_TOKEN)
            jsonContent(updateMemberInfoRequest)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @DisplayName("패스워드 변경 요청시 회원 인증 후 패스워드를 변경한다")
    fun requestChangePasswordThenReturnOKResponse() {
        // given
        val request = ChangeMemberPasswordRequest(1L, "새로운 패스워드")
        every { memberService.changePassword(request.id, request)} just Runs

        // when & then
        mockMvc.patch("${BASE_URL}/me/password") {
            header(AUTHORIZATION, VALID_TOKEN)
            jsonContent(request)
        }.andExpect {
            status { isOk() }
        }
    }
}
