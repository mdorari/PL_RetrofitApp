package com.example.plretrofitapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

const val TAG = "TodoViewModel"

class TodoViewModel : ViewModel() {

    private val repository = TodoRepository()

    private val _allTodos = repository.todos
    val allTodos = _allTodos
    private val _completedTodos = repository.todos.map { todos ->
        todos.filter { it.completed }
    }
    val completedTodos = _completedTodos



    private val _percentageCompleted = repository.todos.map { todos ->
        if (todos.isNotEmpty()) {
            (todos.count { it.completed }.toFloat() / todos.size)
        } else {
            0f
        }
    }
    val percentageCompleted = _percentageCompleted

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            try {
                repository.fetchTodos()
            } catch (e: IOException) {
                Log.d(TAG, "fetchTodos: IOException")
                return@launch
            } catch (e: HttpException) {
                Log.d(TAG, "fetchTodos: HttpException")
                return@launch
            }
        }
    }

}