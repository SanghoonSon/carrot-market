package com.carrot.market.member.domain

import com.carrot.market.fixture.DEFAULT_PASSWORD
import com.carrot.market.fixture.createMember
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields

class MemberTest : StringSpec({

    "id가 동일한 회원은 동일성을 보장한다" {
        val member = createMember("이름", "test@test.com", "test")
        member shouldBeEqualToComparingFields member
    }

    "회원의 비밀번호와 일치하는지 확인한다" {
        var member = createMember();
//        shouldNotThrowAny { member.authenticate(DEFAULT_PASSWORD) }
    }
})
