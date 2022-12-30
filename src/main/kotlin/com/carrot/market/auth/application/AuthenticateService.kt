package com.carrot.market.auth.application

import com.carrot.market.auth.domain.LoginMember
import com.carrot.market.auth.infrastructure.JwtTokenProvider
import com.carrot.market.auth.message.ERROR_ALREADY_REGISTERED_EMAIL
import com.carrot.market.member.application.MemberResponse
import com.carrot.market.member.domain.MemberRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthenticateService(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) {

    fun loginMember(request: LoginRequest): TokenResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        return TokenResponse(tokenProvider.createToken(request.email))
    }

    @Transactional
    fun joinMember(request: JoinMemberRequest): MemberResponse {
        validateJoinMember(request);
        val member = memberRepository.save(request.toMember(passwordEncoder))
        return MemberResponse(member);
    }

    fun findMemberByToken(accessToken: String?): LoginMember {
        validateAccessToken(accessToken)
        val email = tokenProvider.getPayload(accessToken!!)
        val member = memberRepository.findByEmail(email)
            ?: throw EntityNotFoundException()
        return LoginMember(
            id = member.id,
            name = member.name,
            email = member.email
        )
    }

    private fun validateJoinMember(request: JoinMemberRequest) {
        if (memberRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException(ERROR_ALREADY_REGISTERED_EMAIL)
        }
    }

    private fun validateAccessToken(accessToken: String?) {
        if (accessToken == null || !tokenProvider.validateToken(accessToken)) {
            throw InValidAccessTokenException()
        }
    }
}
