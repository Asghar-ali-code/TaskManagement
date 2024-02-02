package com.example.conovotaskmanagement.ui.theme.main_design

import com.example.conovotaskmanagement.db.TaskEntity

sealed class TaskListEvent {
    data class OnDeleteTaskClick(val task: TaskEntity) : TaskListEvent()
    data class OnDoneChangeTask(val task: TaskEntity, val isDone: Boolean) : TaskListEvent()
    object OnUndoDeleteClick : TaskListEvent()
    data class OnTaskClick(val task: TaskEntity) : TaskListEvent()
    object OnAddTaskClick : TaskListEvent()
}
