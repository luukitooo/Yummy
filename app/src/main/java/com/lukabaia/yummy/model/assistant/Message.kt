package com.lukabaia.yummy.model.assistant

data class Message(
    val id: Int,
    val message: String,
    val fromUser: Boolean
)
