package com.carrot.market.member.application

import com.carrot.market.member.domain.MemberRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun findById(id: Long): MemberResponse {
        val member = memberRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }
        return MemberResponse(member)
    }

    @Transactional
    fun updateMember(id: Long, request: UpdateMemberInfoRequest) {
        val member = memberRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }
        member.updateAll(request.toMember())
    }

    fun changePassword(id: Long, request: ChangeMemberPasswordRequest) {
        val member = memberRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }
        member.changePassword(passwordEncoder.encode(request.password))
    }
}
