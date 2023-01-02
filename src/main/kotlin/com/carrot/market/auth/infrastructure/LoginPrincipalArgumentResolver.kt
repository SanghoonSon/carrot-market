package com.carrot.market.auth.infrastructure

import com.carrot.market.auth.application.InValidAccessTokenException
import com.carrot.market.auth.domain.LoginMember
import com.carrot.market.member.domain.MemberRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginPrincipalArgumentResolver(
    private val memberRepository: MemberRepository,
    private val tokenProvider: JwtTokenProvider,
): HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginPrincipal::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
        val accessToken = request?.let { extractAuthToken(request) }
        return findMemberByToken(accessToken)
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

    private fun validateAccessToken(accessToken: String?) {
        if (accessToken == null || !tokenProvider.validateToken(accessToken)) {
            throw InValidAccessTokenException()
        }
    }
}
