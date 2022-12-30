package com.carrot.market.auth.application

import com.carrot.market.member.domain.Address
import com.carrot.market.member.domain.Member
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.security.crypto.password.PasswordEncoder

data class LoginRequest(
    @field:NotNull(message = "이메일은 필수값 입니다.")
    @field:Email(message = "이메일 주소가 올바르지 않습니다.")
    val email: String,

    @field:NotEmpty(message = "패스워드는 필수값입니다.")
    val password: String
)

data class JoinMemberRequest(
    @field:Pattern(regexp = "[가-힣]{1,10}", message = "이름이 올바르지 않습니다.")
    val name: String,

    @field:Email(message = "이메일 주소가 올바르지 않습니다.")
    val email: String,

    @field:NotEmpty(message = "패스워드는 필수값입니다.")
    val password: String,

    val street: String = "",
    val city: String = "",
    val zipcode: String = ""
) {
    fun toMember(passwordEncoder: PasswordEncoder): Member {
        return Member(
            name,
            email,
            passwordEncoder.encode(password),
            Address(street, city, zipcode)
        )
    }
}

data class TokenResponse (
    val accessToken: String
)
