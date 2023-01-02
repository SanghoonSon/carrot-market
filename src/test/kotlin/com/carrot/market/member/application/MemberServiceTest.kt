package com.carrot.market.member.application

import com.carrot.market.fixture.createMember
import com.carrot.market.member.domain.Address
import com.carrot.market.member.domain.MemberRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

val memberRepository: MemberRepository = mockk()
val passwordEncoder: PasswordEncoder = mockk()

@InjectMockKs
val memberService: MemberService = MemberService(memberRepository, passwordEncoder)

internal class MemberServiceTest(): BehaviorSpec({

    afterEach {
        clearAllMocks()
    }

    Given("내정보 조회 요청이 주어진 경우") {
        val member = createMember()
        every { memberRepository.findById(member.id) } returns Optional.of(member)

        When("내정보 조회시") {
            val memberResponse = memberService.findById(member.id)

            Then("내정보를 반환한다") {
                memberResponse.id shouldBe member.id
                memberResponse.name shouldBe member.name
                memberResponse.email shouldBe member.email

                verify(exactly = 1) { memberRepository.findById(member.id) }
            }
        }
    }

    Given("내정보 수정 요청이 주어진 경우") {
        val address = Address("123-456", "경기도 성남시 분당구 건물대로132", "잘지어진 건물 113호")
        val updateMemberInfoRequest = UpdateMemberInfoRequest("수정된이름", address)
        val member = createMember()
        every { memberRepository.findById(member.id) } returns Optional.of(member)

        When("내정보 수정시") {
            memberService.updateMember(member.id, updateMemberInfoRequest)

            Then("수정에 성공한다") {
                member.name shouldBe updateMemberInfoRequest.name
                member.address shouldBe updateMemberInfoRequest.address?.let {
                    Address(it.zipcode, it.address, it.details)
                }
                verify(exactly = 1) { memberRepository.findById(member.id) }
            }
        }
    }

    Given("패스워드 변경 요청이 주어진 경우") {
        val member = createMember()
        val newPassword = "새로운 패스워드"
        val request = ChangeMemberPasswordRequest(member.id, newPassword)
        every { memberRepository.findById(member.id) } returns Optional.of(member)
        every { passwordEncoder.encode(newPassword) } returns newPassword

        When("패스워드 변경시") {
            memberService.changePassword(request.id, request)

            Then("패스워드 변경에 성공한다") {
                member.password shouldBe newPassword
                verify(exactly = 1) { memberRepository.findById(member.id) }
                verify(exactly = 1) { passwordEncoder.encode(newPassword) }
            }
        }
    }
})
