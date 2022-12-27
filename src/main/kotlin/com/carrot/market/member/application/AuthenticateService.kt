package com.carrot.market.member.application

import com.carrot.market.member.domain.MemberRepository
import com.carrot.market.member.message.ERROR_ALREADY_REGISTERED_EMAIL
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthenticateService(
    private val memberRepository: MemberRepository
) {

    fun joinMember(request: RegisterMemberRequest): MemberResponse {
        validateJoinMember(request);
        val member = memberRepository.save(request.toMember())
        return MemberResponse(member);
    }

    private fun validateJoinMember(request: RegisterMemberRequest) {
        if(memberRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException(ERROR_ALREADY_REGISTERED_EMAIL)
        }
    }
}
