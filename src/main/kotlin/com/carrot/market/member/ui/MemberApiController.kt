package com.carrot.market.member.ui

import com.carrot.market.member.application.MemberResponse
import com.carrot.market.member.application.AuthenticateService
import com.carrot.market.member.application.RegisterMemberRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/members")
class MemberApiController(
    private val authenticateService: AuthenticateService
) {

    @PostMapping("/register")
    fun joinMember(@RequestBody request: RegisterMemberRequest): ResponseEntity<MemberResponse> {
        val response = authenticateService.joinMember(request)
        return ResponseEntity.created(URI.create("/api/members/${response.id}")).build()
    }
}
