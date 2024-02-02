package com.example.conovotaskmanagement.db


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    val title: String,
    val description: String?,
    val isTaskCompleted: Boolean,
    @PrimaryKey val id: Int? =null
)
