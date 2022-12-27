package com.carrot.market.member.domain

import com.carrot.market.fixture.createMember
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import support.test.JpaEntityRepositoryTest

@JpaEntityRepositoryTest
class MemberRepositoryTest(
    private val memberRepository: MemberRepository
) : ExpectSpec({
    extension(SpringTestExtension(SpringTestLifecycleMode.Root))

    context("회원 조회") {
        val expectedMember = memberRepository.save(createMember())

        expect("이메일 주소로 회원을 조회한다") {
            val findMember = memberRepository.findByEmail(expectedMember.email)
            findMember shouldBe expectedMember
        }

        expect("이메일 주소로 회원이 등록되어 있는지 확인한다") {
            val existsByEmail = memberRepository.existsByEmail(expectedMember.email)
            existsByEmail shouldBe true
        }
    }
})
