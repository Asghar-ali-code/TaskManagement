package com.example.conovotaskmanagement.repo

import com.example.conovotaskmanagement.db.Dao
import com.example.conovotaskmanagement.db.TaskEntity
import kotlinx.coroutines.flow.Flow

class Repository(
    private val dao: Dao
) : RepositoryInf {

    override suspend fun insertTodo(todo: TaskEntity) {
        dao.insertTask(todo)
    }

    override suspend fun deleteTodo(todo: TaskEntity) {
        dao.deleteTask(todo)
    }

    override suspend fun getTodoById(Id: Int): TaskEntity? {
        return dao.getTaskById(Id)
    }

    override fun getTodos(): Flow<List<TaskEntity>> {
        return dao.getTasks()
    }

}