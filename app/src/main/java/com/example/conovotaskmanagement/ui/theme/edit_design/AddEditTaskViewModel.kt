package com.example.conovotaskmanagement.ui.theme.edit_design

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conovotaskmanagement.db.TaskEntity
import com.example.conovotaskmanagement.repo.RepositoryInf
import com.example.conovotaskmanagement.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val repository: RepositoryInf,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var task by mutableStateOf<TaskEntity?>(null)


    var title by mutableStateOf("")


    var description by mutableStateOf("")


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("taskId")
        if (todoId != -1) {
            viewModelScope.launch {
                if (todoId != null) {
                    repository.getTodoById(todoId)?.let { task ->
                        title = task.title
                        description = task.description ?: ""
                        this@AddEditTaskViewModel.task = task
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title
            }

            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }

            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "The title can't be empty"
                            )
                        )
                        return@launch
                    }
                    repository.insertTodo(
                        TaskEntity(
                            title = title,
                            description = description,
                            isTaskCompleted = task?.isTaskCompleted ?: false,
                            id = task?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    //send events to update the ui
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}