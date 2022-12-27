package com.carrot.market.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(

    @Column
    var street: String,

    @Column
    var city: String,

    @Column
    var zipcode: String
)
