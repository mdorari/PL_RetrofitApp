package com.example.plretrofitapp

import kotlinx.coroutines.flow.MutableStateFlow

class TodoRepository {


    private val api = RetrofitInstance.api

    val todos = MutableStateFlow<List<Todo>>(emptyList())

    suspend fun fetchTodos(){
        val result = api.getTodos()
        todos.value = result.body()!!
    }
}
