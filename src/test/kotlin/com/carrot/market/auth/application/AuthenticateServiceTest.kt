package com.carrot.market.auth.application

import com.carrot.market.auth.infrastructure.JwtTokenProvider
import com.carrot.market.auth.message.ERROR_ALREADY_REGISTERED_EMAIL
import com.carrot.market.auth.message.ERROR_AUTHENTICATION_FAIL
import com.carrot.market.fixture.DEFAULT_EMAIL
import com.carrot.market.fixture.DEFAULT_NAME
import com.carrot.market.fixture.DEFAULT_PASSWORD
import com.carrot.market.fixture.createMember
import com.carrot.market.member.domain.MemberRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveMinLength
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder

val authenticationManager: AuthenticationManager = mockk()
val passwordEncoder: PasswordEncoder = mockk()
val jwtTokenProvider: JwtTokenProvider = mockk()
val memberRepository: MemberRepository = mockk()

@InjectMockKs
val authenticateService = AuthenticateService(authenticationManager, passwordEncoder, jwtTokenProvider, memberRepository)

internal class AuthenticateServiceTest : BehaviorSpec({

    val joinRequest = JoinMemberRequest(
        DEFAULT_NAME,
        DEFAULT_EMAIL,
        DEFAULT_PASSWORD
    )

    Given("신규 회원의 가입 요청이 주어진경우") {
        val member = createMember()
        every { memberRepository.save(any()) } returns member

        When("신규 회원 등록시") {
            every { memberRepository.existsByEmail(any()) } returns false
            every { passwordEncoder.encode(any()) } returns "인코딩된비밀번호"
            val response = authenticateService.joinMember(joinRequest)

            Then("회원 가입이 완료된다") {
                response.id shouldBe member.id
                response.name shouldBe member.name
                response.email shouldBe member.email

                verify(exactly = 1) { memberRepository.existsByEmail(any()) }
                verify(exactly = 1) { memberRepository.save(any()) }
            }
        }

        When("신규 회원 검증시 이메일이 중복됬을때") {
            every { memberRepository.existsByEmail(any()) } returns true
            val exception = shouldThrow<IllegalArgumentException> {
                authenticateService.joinMember(joinRequest)
            }

            Then("회원 가입에 실패한다") {
                exception.message shouldBe ERROR_ALREADY_REGISTERED_EMAIL
                verify(exactly = 1) { memberRepository.existsByEmail(any()) }
                verify(exactly = 0) { memberRepository.save(any()) }
            }
        }
    }

    Given("회원 로그인 요청이 주어진경우") {
        val loginRequest = LoginRequest(DEFAULT_EMAIL, DEFAULT_PASSWORD)
        every { jwtTokenProvider.createToken(any()) } returns "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

        When("회원 로그인시") {
            every { authenticationManager.authenticate(any()) } returns UsernamePasswordAuthenticationToken(
                DEFAULT_EMAIL,
                DEFAULT_PASSWORD
            )

            val tokenResponse = authenticateService.loginMember(loginRequest)

            Then("로그인 완료 후 생성 된 토큰 정보를 응답한다") {
                tokenResponse.accessToken shouldHaveMinLength 10
                verify(exactly = 1) { authenticationManager.authenticate(any()) }
                verify(exactly = 1) { jwtTokenProvider.createToken(any()) }
            }
        }

        When("회원 로그인시 가입된 회원이 아닌 경우") {
            every { authenticationManager.authenticate(any()) } throws AuthorizationException()

            val exception = shouldThrow<AuthorizationException> {
                authenticateService.loginMember(loginRequest)
            }

            Then("인증 처리가 되지않아 로그인에 실패한다") {
                exception.message shouldBe ERROR_AUTHENTICATION_FAIL
            }
        }
    }

    afterEach { clearAllMocks() }
})
