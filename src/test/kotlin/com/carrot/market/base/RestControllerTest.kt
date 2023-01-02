package com.carrot.market.base

import com.carrot.market.auth.application.AuthorizationException
import com.carrot.market.auth.domain.LoginMember
import com.carrot.market.auth.infrastructure.AUTHORIZATION
import com.carrot.market.auth.infrastructure.BEARER_TYPE
import com.carrot.market.auth.infrastructure.LoginPrincipal
import com.carrot.market.auth.infrastructure.LoginPrincipalArgumentResolver
import com.carrot.market.global.ui.ApiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.slot
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.ContentResultMatchersDsl
import org.springframework.test.web.servlet.result.HeaderResultMatchersDsl
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.filter.CharacterEncodingFilter

const val VALID_TOKEN = "valid_token"

abstract class RestControllerTest {

    @MockkBean
    private lateinit var loginPrincipalArgumentResolver: LoginPrincipalArgumentResolver

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(webApplicationContext: WebApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .build()

        loginPrincipalArgumentResolver.also {
            slot<MethodParameter>().also { slot ->
                every { it.supportsParameter(capture(slot)) } answers {
                    slot.captured.hasParameterAnnotation(LoginPrincipal::class.java)
                }
            }
            slot<NativeWebRequest>().also { slot ->
                every { it.resolveArgument(any(), any(), capture(slot), any()) } answers {
                    if(!hasAuthToken(slot)) {
                        throw AuthorizationException()
                    }
                    LoginMember(id = 1L, name = "관리자", email = "admin@test.com")
                }
            }
        }
    }

    private fun hasAuthToken(slot: CapturingSlot<NativeWebRequest>): Boolean {
        val header = slot.captured.getHeader(AUTHORIZATION) ?: return false
        return header.startsWith(BEARER_TYPE) || header.startsWith(VALID_TOKEN)
    }

    fun MockHttpServletRequestDsl.jsonContent(value: Any) {
        content = objectMapper.writeValueAsString(value)
        contentType = MediaType.APPLICATION_JSON
    }

    fun HeaderResultMatchersDsl.location(value: String) {
        string("Location", value)
    }

    fun ContentResultMatchersDsl.success(value: Any) {
        json(objectMapper.writeValueAsString(ApiResponse.success(value)), true)
    }

    fun ContentResultMatchersDsl.fail(message: String) {
        json(objectMapper.writeValueAsString(ApiResponse.fail(message)), true)
    }
}
