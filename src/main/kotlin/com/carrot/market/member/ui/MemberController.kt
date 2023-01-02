package com.carrot.market.member.ui

import com.carrot.market.auth.domain.LoginMember
import com.carrot.market.auth.infrastructure.LoginPrincipal
import com.carrot.market.global.ui.ApiResponse
import com.carrot.market.member.application.ChangeMemberPasswordRequest
import com.carrot.market.member.application.MemberResponse
import com.carrot.market.member.application.MemberService
import com.carrot.market.member.application.UpdateMemberInfoRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/me")
    fun retrieveMe(
        @LoginPrincipal loginMember: LoginMember
    ) : ResponseEntity<ApiResponse<MemberResponse>> {
        val response = memberService.findById(loginMember.id)
        return ResponseEntity.ok().body(ApiResponse.success(response))
    }

    @PutMapping("/me")
    fun updateMe(
        @LoginPrincipal loginMember: LoginMember,
        @RequestBody request: UpdateMemberInfoRequest
    ): ResponseEntity<Any> {
        memberService.updateMember(loginMember.id, request)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/me/password")
    fun changePassword(
        @LoginPrincipal loginMember: LoginMember,
        @RequestBody request: ChangeMemberPasswordRequest
    ): ResponseEntity<Any> {
        memberService.changePassword(loginMember.id, request)
        return ResponseEntity.ok().build()
    }
}
