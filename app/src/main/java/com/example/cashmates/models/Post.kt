package com.example.cashmates.models

data class Post (
    val amount: String = "",
    val createdBy: User = User(),
    val createdAt: Long = 0L,
    val locate: String = "")