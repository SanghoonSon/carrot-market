package com.carrot.market.member.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByEmail(email:String): Member?

    fun existsByEmail(email:String): Boolean
}
