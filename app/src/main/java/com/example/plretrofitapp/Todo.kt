package com.example.plretrofitapp

data class Todo(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)