package com.example.conovotaskmanagement.db


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM task WHERE id = :Id")
    suspend fun getTaskById(Id: Int): TaskEntity?

    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<TaskEntity>>
}