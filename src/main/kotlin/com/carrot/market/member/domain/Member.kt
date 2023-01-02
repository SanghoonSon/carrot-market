package com.carrot.market.member.domain

import com.carrot.market.global.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "members")
class Member(

    @Column(nullable = false, length = 30)
    var name: String,

    @Column(unique = true, nullable = false)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Embedded
    var address: Address? = null,

    id: Long = 0L
) : BaseEntity(id) {

    constructor(name: String, address: Address?): this(name, "", "", address)

    fun updateAll(member: Member) {
        this.name = member.name
        this.address = member.address
    }

    fun changePassword(password: String) {
        this.password = password
    }
}
