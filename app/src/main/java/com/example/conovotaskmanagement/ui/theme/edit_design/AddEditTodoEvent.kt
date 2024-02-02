package com.example.conovotaskmanagement.ui.theme.edit_design

sealed class AddEditTodoEvent{
    data class OnTitleChange(val title: String) : AddEditTodoEvent()
    data class OnDescriptionChange(val description: String) : AddEditTodoEvent()
    object OnSaveTodoClick : AddEditTodoEvent()
}
