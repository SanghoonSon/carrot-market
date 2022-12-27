package com.carrot.market.fixture

import com.carrot.market.member.domain.Address
import com.carrot.market.member.domain.Member

const val DEFAULT_NAME: String = "유저1"
const val DEFAULT_EMAIL: String = "test@test.com"
const val DEFAULT_PASSWORD: String = "test"
const val DEFAULT_STREET: String = "street"
const val DEFAULT_CITY: String = "city"
const val DEFAULT_ZIPCODE: String = "zipcode"


fun createMember(
    name: String = DEFAULT_NAME,
    email: String = DEFAULT_EMAIL,
    password: String = DEFAULT_PASSWORD
): Member {
    return Member(
        name,
        email,
        password,
        Address(DEFAULT_STREET, DEFAULT_CITY, DEFAULT_ZIPCODE)
    )
}
