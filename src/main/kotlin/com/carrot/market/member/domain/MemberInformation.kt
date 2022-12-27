package com.carrot.market.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class MemberInformation(
    @Column(nullable = false, length = 30)
    val name: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String
)
