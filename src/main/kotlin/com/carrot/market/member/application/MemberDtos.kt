package com.carrot.market.member.application

import com.carrot.market.member.domain.Member

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
