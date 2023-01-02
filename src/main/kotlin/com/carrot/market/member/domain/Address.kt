package com.carrot.market.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(

    @Column(length = 7)
    var zipcode: String,

    @Column(length = 100)
    var address: String,

    @Column(length = 100)
    var details: String

)
