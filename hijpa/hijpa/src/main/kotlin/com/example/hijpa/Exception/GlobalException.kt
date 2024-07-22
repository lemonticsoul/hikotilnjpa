package com.example.hijpa.Exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

enum class MyErrorCode(val statusCode:Int,val message:String){

    SEVER_ERROR

}

data class MyErrorResponse(
    val statusCode: MyErrorCode,
    val errorMessage:String
)
class MyBookShelfException(message:String):RuntimeException(message)

@ControllerAdvice()
class GlobalException {

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalArgumentException(ex:IllegalStateException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)

    }

    @ExceptionHandler(MyBookShelfException::class)
    fun handleMyBookShelfException(ex:MyBookShelfException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }
}
