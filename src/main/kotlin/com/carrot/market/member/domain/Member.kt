package com.carrot.market.member.domain

import com.carrot.market.global.domain.BaseEntity
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.OverridesAttribute

@Entity
@Table(name = "members")
class Member(

    @Column(nullable = false, length = 30)
    var name: String,

    @Column(unique = true, nullable = false)
    var email: String,

    @AttributeOverride(name = "user_password", column = Column(nullable = false))
    var password: String,

    @Embedded
    var address: Address,

    id: Long = 0L
) : BaseEntity(id) {

    fun authenticate(password: String): Boolean {
        return this.password == password;
    }
}
