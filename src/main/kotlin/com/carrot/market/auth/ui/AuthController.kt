package com.carrot.market.auth.ui

import com.carrot.market.auth.application.JoinMemberRequest
import com.carrot.market.auth.application.LoginRequest
import com.carrot.market.auth.application.TokenResponse
import com.carrot.market.global.ui.ApiResponse
import com.carrot.market.auth.application.AuthenticateService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticateService: AuthenticateService
) {

    @PostMapping("/login")
    fun loginMember(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<ApiResponse<TokenResponse>> {
        val response = authenticateService.loginMember(request)
        return ResponseEntity.ok().body(ApiResponse.success(response))
    }

    @PostMapping("/join")
    fun joinMember(
        @Valid @RequestBody request: JoinMemberRequest
    ): ResponseEntity<Any> {
        val response = authenticateService.joinMember(request)
        return ResponseEntity.created(URI.create("/api/v1/members/${response.id}")).build()
    }
}
