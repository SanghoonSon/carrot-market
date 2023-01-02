package com.carrot.market.member.domain

import com.carrot.market.fixture.DEFAULT_ADDRESS
import com.carrot.market.fixture.createMember
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName

@DisplayName("회원 도메인 테스트")
class MemberTest : StringSpec({

    "id가 동일한 회원은 동일성을 보장한다" {
        val member = createMember("이름", "test@test.com", "test")
        member shouldBeEqualToComparingFields member
    }

    "회원 정보를 수정한다" {
        // given
        val member = createMember("이름", "test@test.com", "test")
        val updateValues = createMember(name = "수정된이름", address = DEFAULT_ADDRESS)

        // when
        member.updateAll(updateValues)

        // then
        member.name shouldBe updateValues.name
        member.address shouldBe updateValues.address
    }
})
