package com.carrot.market.fixture

import com.carrot.market.member.domain.Address
import com.carrot.market.member.domain.Member

const val DEFAULT_NAME: String = "유저1"
const val DEFAULT_EMAIL: String = "test@test.com"
const val DEFAULT_PASSWORD: String = "test"
val DEFAULT_ADDRESS: Address = Address("123-456", "경기도 성남시 분당구 건물대로132", "잘지어진 건물 113호")

fun createMember(
    name: String = DEFAULT_NAME,
    email: String = DEFAULT_EMAIL,
    password: String = DEFAULT_PASSWORD,
    address: Address? = null
): Member {
    return Member(
        name,
        email,
        password,
        address
    )
}
