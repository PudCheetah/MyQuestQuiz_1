package com.example.myquestquiz_1.QuestPageActivity

import QuestPageViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuestPageViewModelFactory(var application: Application, var bankID: Long, var numExpect: Int): ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestPageViewModel(application, bankID, numExpect) as T
    }
}