package com.example.todolist.data.repository

import com.example.todolist.domain.model.Task
import com.example.todolist.domain.repository.TaskRepository


class TaskRepositoryImpl : TaskRepository {
    private val tasks = mutableListOf(
        Task(1, "Acheter du pain", "Passer à la boulangerie avant 19h"),
        Task(2, "Faire du sport", "Séance de 30 minutes de cardio"),
        Task(3, "Apprendre Kotlin", "Terminer le cours sur la Clean Architecture")
    )

    override fun getTasks() = tasks.toList()

    override fun addTask(title: String, description: String) {
        val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
        tasks.add(Task(nextId, title, description))
    }

    override fun updateTask(id: Int, title: String, description: String) {
        val index = tasks.indexOfFirst { it.id == id }
        if (index != -1) tasks[index] = tasks[index].copy(title = title, description = description)
    }

    override fun deleteTask(id: Int) {
        tasks.removeAll { it.id == id }
    }

    override fun toggleTaskStatus(id: Int) {
        val index = tasks.indexOfFirst { it.id == id }
        if (index != -1) tasks[index] = tasks[index].copy(isDone = !tasks[index].isDone)
    }
}
