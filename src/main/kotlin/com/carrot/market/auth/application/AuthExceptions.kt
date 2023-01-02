package com.carrot.market.auth.application

import com.carrot.market.auth.message.ERROR_AUTHENTICATION_FAIL
import com.carrot.market.auth.message.ERROR_INVALID_ACCESS_TOKEN
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthorizationException : RuntimeException(ERROR_AUTHENTICATION_FAIL)

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class InValidAccessTokenException : RuntimeException(ERROR_INVALID_ACCESS_TOKEN)
