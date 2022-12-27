package com.carrot.market.member.application

import com.carrot.market.fixture.*
import com.carrot.market.member.domain.MemberRepository
import com.carrot.market.member.message.ERROR_ALREADY_REGISTERED_EMAIL
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

internal class AuthenticateServiceTest : BehaviorSpec({
    val memberRepository = mockk<MemberRepository>()
    val authenticateService = AuthenticateService(memberRepository);

    Given("신규 회원의 가입 요청이 주어진경우") {
        val member = createMember();
        every { memberRepository.save(any()) } returns member;

        val request = RegisterMemberRequest(
            DEFAULT_NAME,
            DEFAULT_EMAIL,
            DEFAULT_PASSWORD,
            DEFAULT_STREET,
            DEFAULT_CITY,
            DEFAULT_ZIPCODE,
        )

        When("신규 회원 등록시") {
            every { memberRepository.existsByEmail(any()) } returns false;
            val response = authenticateService.joinMember(request)

            Then("회원 가입이 완료된다") {
                response.id shouldBe member.id
                response.name shouldBe member.name
                response.email shouldBe member.email
            }
        }

        When("신규 회원 검증시 이메일이 중복됬을때") {
            every { memberRepository.existsByEmail(any()) } returns true;
            val excepiton = shouldThrow<IllegalArgumentException> {
                authenticateService.joinMember(request)
            }

            Then("회원 가입에 실패한다") {
                excepiton.message shouldBe ERROR_ALREADY_REGISTERED_EMAIL
            }
        }
    }

    afterEach { clearAllMocks() }
})
