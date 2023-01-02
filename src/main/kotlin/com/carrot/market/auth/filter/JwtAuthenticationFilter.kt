package com.carrot.market.auth.filter

import com.carrot.market.auth.application.InValidAccessTokenException
import com.carrot.market.auth.infrastructure.JwtTokenProvider
import com.carrot.market.auth.infrastructure.extractAuthToken
import com.carrot.market.global.ui.ApiResponse
import com.carrot.market.member.domain.MemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val objectMapper: ObjectMapper,
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authToken = extractAuthToken(request)
        authToken?.let {
            try {
                validateToken(authToken)
                SecurityContextHolder.getContext().authentication = getAuthenticationToken(request, authToken)
            } catch (e: InValidAccessTokenException) {
                setErrorResponse(response, e)
                return
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun validateToken(authToken: String) {
        if (!jwtTokenProvider.validateToken(authToken)) {
            throw InValidAccessTokenException()
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, e: Exception){
        val apiResponse = ApiResponse.fail(e.message)
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(apiResponse))
    }

    private fun getAuthenticationToken(request: HttpServletRequest, jwtToken: String): Authentication {
        val memberEmail = jwtTokenProvider.getPayload(jwtToken)
        val member = memberRepository.findByEmail(memberEmail)
            ?: throw UsernameNotFoundException("$memberEmail can not found")

        val authorities: List<GrantedAuthority> = mutableListOf(SimpleGrantedAuthority("USER"))
        val authentication = UsernamePasswordAuthenticationToken(member.email, member.password, authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        return authentication
    }
}
