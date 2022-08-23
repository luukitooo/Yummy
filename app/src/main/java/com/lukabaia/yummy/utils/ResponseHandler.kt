package com.lukabaia.yummy.utils

sealed class ResponseHandler {
    class Success<T>(val result: T): ResponseHandler()
    class Error(val errorMessage: String): ResponseHandler()
    class Loader(val isLoading: Boolean): ResponseHandler()
}