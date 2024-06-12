package com.example.myquestquiz_1.QuestionInfoActivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuestionInfoViewModelFactory(var application: Application, var questionIDFromIntent: Long): ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionInfoViewModel(application, questionIDFromIntent) as T
    }
}