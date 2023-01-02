package com.carrot.market.global.ui

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(value = [MissingKotlinParameterException::class])
    fun missingKotlinParameterExceptionHandler(
        e: MissingKotlinParameterException
    ): ResponseEntity<ApiResponse<Unit>> {
        val response = ApiResponse.fail("${e.parameter} is missing or null")
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(value = [InvalidFormatException::class])
    fun invalidFormatExceptionHandler(
        e: InvalidFormatException
    ): ResponseEntity<ApiResponse<Unit>> {
        val response = ApiResponse.fail("${e.path.first()} : ${e.value} is not acceptable")
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidExceptionHandler(
        e: MethodArgumentNotValidException
    ): ResponseEntity<ApiResponse<Unit>> {
        val errorMessage = e.fieldErrors.joinToString("\n") {
            val fieldMessage = it.defaultMessage ?: "유효하지 않은 값입니다."
            return@joinToString "${it.field} : $fieldMessage"
        }
        val response = ApiResponse.fail(errorMessage)
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    fun missingServletRequestParameterExceptionHandler(
        e: MissingServletRequestParameterException
    ): ResponseEntity<ApiResponse<Unit>> {
        val response = ApiResponse.fail("${e.parameterName} is missing or null.")
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
    fun methodArgumentTypeMismatchExceptionHandler(
        e: MethodArgumentTypeMismatchException
    ): ResponseEntity<ApiResponse<Unit>> {
        val response = ApiResponse.fail("${e.name} : ${e.value} is not acceptable.")
        return ResponseEntity.badRequest().body(response)
    }
}
