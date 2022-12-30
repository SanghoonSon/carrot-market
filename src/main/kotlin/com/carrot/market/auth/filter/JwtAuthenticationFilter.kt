package com.carrot.market.auth.filter

import com.carrot.market.auth.infrastructure.JwtTokenProvider
import com.carrot.market.auth.infrastructure.extractAuthToken
import com.carrot.market.member.domain.MemberRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authToken = extractAuthToken(request)
        if (jwtTokenProvider.validateToken(authToken)) {
            SecurityContextHolder.getContext().authentication = getAuthenticationToken(request, authToken)
        }
        filterChain.doFilter(request, response)
    }

    private fun getAuthenticationToken(request: HttpServletRequest, jwtToken: String): Authentication {
        val memberEmail = jwtTokenProvider.getPayload(jwtToken)
        val member = memberRepository.findByEmail(memberEmail)
            ?: throw UsernameNotFoundException("$memberEmail can not found")

        val authentication = UsernamePasswordAuthenticationToken(member.email, member.password)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        return authentication
    }
}
