package com.example.conovotaskmanagement.ui.theme.main_design

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conovotaskmanagement.db.TaskEntity
import com.example.conovotaskmanagement.repo.RepositoryInf
import com.example.conovotaskmanagement.util.UiEvent
import com.example.conovotaskmanagement.util.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: RepositoryInf
) : ViewModel() {

    val tasks = repository.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: TaskEntity? = null

    fun onEvent(event: TaskListEvent) {
        when (event) {
            is TaskListEvent.OnDeleteTaskClick -> {
                viewModelScope.launch {
                    deletedTodo = event.task
                    repository.deleteTodo(event.task)
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            "Task deleted",
                            "Undo"
                        )
                    )
                }
            }

            is TaskListEvent.OnAddTaskClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TASK))
            }

            is TaskListEvent.OnTaskClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TASK + "?taskId=${event.task.id}"))
            }

            is TaskListEvent.OnDoneChangeTask -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.task.copy(
                            isTaskCompleted = event.isDone
                        )
                    )
                }
            }

            is TaskListEvent.OnUndoDeleteClick -> {
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }
            }
        }
    }

    // sending event to update the ui and perfom task
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}