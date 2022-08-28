package com.lukabaia.yummy.utils

sealed class ResultOf<T> {
    class Success<T> : ResultOf<T>()
    data class Failure<T>(val message: T? = null) : ResultOf<T>()
}
