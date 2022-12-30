package com.carrot.market.member.domain

import com.carrot.market.fixture.createMember
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import org.junit.jupiter.api.DisplayName

@DisplayName("회원 도메인 테스트")
class MemberTest : StringSpec({

    "id가 동일한 회원은 동일성을 보장한다" {
        val member = createMember("이름", "test@test.com", "test")
        member shouldBeEqualToComparingFields member
    }
})
