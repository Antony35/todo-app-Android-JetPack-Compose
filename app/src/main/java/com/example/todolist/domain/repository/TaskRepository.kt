package com.example.todolist.domain.repository

import com.example.todolist.domain.model.Task

interface TaskRepository {
    fun getTasks(): List<Task>
    fun addTask(title: String, description: String)
    fun updateTask(id: Int, title: String, description: String)
    fun deleteTask(id: Int)
    fun toggleTaskStatus(id: Int)
}
