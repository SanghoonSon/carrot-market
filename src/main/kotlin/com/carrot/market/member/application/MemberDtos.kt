package com.carrot.market.member.application

import com.carrot.market.member.domain.Address
import com.carrot.market.member.domain.Member
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive

data class MemberResponse(
    val id: Long,
    val name: String,
    val email: String,
    var address: Address?
) {
    constructor(member: Member) : this(
        member.id,
        member.name,
        member.email,
        member.address
    )

    constructor(id: Long, name: String, email:String) : this(id, name, email, null)
}

data class UpdateMemberInfoRequest(
    @field:Pattern(regexp = "[가-힣]{1,10}", message = "이름이 올바르지 않습니다.")
    val name: String,
    var address: Address?
) {
    fun toMember(): Member {
        return Member(name, address)
    }
}

data class ChangeMemberPasswordRequest(
    @field:Positive(message = "ID는 필수값입니다.")
    val id: Long,

    @field:NotEmpty(message = "패스워드는 필수값입니다.")
    val password: String,
)
