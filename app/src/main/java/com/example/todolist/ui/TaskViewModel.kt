package com.example.todolist.ui

import androidx.lifecycle.ViewModel
import com.example.todolist.data.repository.TaskRepositoryImpl
import com.example.todolist.domain.model.Task
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel(
    private val repository: TaskRepository = TaskRepositoryImpl()
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        loadTasks()
    }

    private fun loadTasks() {
        _tasks.value = repository.getTasks()
    }

    fun addTask(title: String, description: String) {
        if (title.isNotBlank()) {
            repository.addTask(title, description)
            loadTasks()
        }
    }

    fun updateTask(id: Int, title: String, description: String) {
        if (title.isNotBlank()) {
            repository.updateTask(id, title, description)
            loadTasks()
        }
    }

    fun deleteTask(id: Int) {
        repository.deleteTask(id)
        loadTasks()
    }

    fun toggleTaskStatus(id: Int) {
        repository.toggleTaskStatus(id)
        loadTasks()
    }
}
