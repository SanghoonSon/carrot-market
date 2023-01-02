package com.carrot.market.auth.infrastructure

import jakarta.servlet.http.HttpServletRequest

const val AUTHORIZATION = "Authorization"
const val BEARER_TYPE = "Bearer"

fun extractAuthToken(request: HttpServletRequest): String? {
    request.getHeaders(AUTHORIZATION).asIterator().forEach {
        if (isBearerType(it)) {
            return toAuthToken(it)
        }
    }
    return null
}

private fun toAuthToken(authHeader: String): String {
    val authHeaderValue: String = authHeader.substring(BEARER_TYPE.length).trim { it <= ' ' }
    return if (authHeaderValue.contains(",")) {
        authHeaderValue.split(",".toRegex()).toTypedArray()[0]
    } else authHeaderValue
}

private fun isBearerType(it: String): Boolean = it.startsWith(BEARER_TYPE)
