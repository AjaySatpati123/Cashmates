package com.example.cashmates.models

data class Note (
    val detail: String = "",
    val createdBy: User = User(),
    val time: Long = 0L)