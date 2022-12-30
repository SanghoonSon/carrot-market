package com.carrot.market.global.ui

data class ApiResponse<T> (
    val message: String? = "",
    val body: T? = null
) {
    companion object {
        fun <T> success(body: T?): ApiResponse<T> = ApiResponse(body = body)
        fun fail(message: String?): ApiResponse<Unit> = ApiResponse(message = message)
    }
}
