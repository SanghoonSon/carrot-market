package com.carrot.market.member.application

import com.carrot.market.member.domain.Address
import com.carrot.market.member.domain.Member
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class RegisterMemberRequest(
    @field:Pattern(regexp = "[가-힣]{1,10}", message = "이름이 올바르지 않습니다.")
    val name: String,

    @field:Email(message = "이메일 주소가 올바르지 않습니다.")
    val email: String,

    @field:NotEmpty(message = "패스워드는 필수값입니다.")
    val password: String,

    val street: String,
    val city: String,
    val zipcode: String
) {
    fun toMember(): Member {
        return Member(
            name,
            email,
            password,
            Address(street, city, zipcode)
        )
    }
}

data class MemberResponse(
    val id: Long,
    val name: String,
    val email: String
) {
    constructor(member: Member) : this(
        member.id,
        member.name,
        member.email
    )
}
