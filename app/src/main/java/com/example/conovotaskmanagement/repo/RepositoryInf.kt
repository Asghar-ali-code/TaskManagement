package com.example.conovotaskmanagement.repo

import com.example.conovotaskmanagement.db.TaskEntity
import kotlinx.coroutines.flow.Flow

interface RepositoryInf {

    suspend fun insertTodo(todo: TaskEntity)

    suspend fun deleteTodo(todo: TaskEntity)

    suspend fun getTodoById(Id: Int): TaskEntity?

    fun getTodos(): Flow<List<TaskEntity>>
}