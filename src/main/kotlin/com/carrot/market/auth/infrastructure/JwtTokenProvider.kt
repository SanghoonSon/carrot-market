package com.carrot.market.auth.infrastructure

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.token.secret-key}")
    secretKey: String,

    @Value("\${security.jwt.token.expire-length}")
    private val validityInMilliseconds: Long
) {
    private val secretKey: Key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    fun createToken(payload: String): String {
        val claims = Jwts.claims().setSubject(payload)
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getPayload(token: String): String = getClaims(token).body.subject

    fun validateToken(token: String): Boolean {
        return try {
            !getClaims(token).body.expiration.before(Date())
        } catch (e: JwtException) {
            return false
        } catch (e: IllegalArgumentException) {
            return false
        }
    }

    private fun getClaims(token: String) = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
}
