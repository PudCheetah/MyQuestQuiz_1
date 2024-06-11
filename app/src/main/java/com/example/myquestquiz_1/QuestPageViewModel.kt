package com.example.myquestquiz_1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myquestquiz_1.MyDatabase.MyRepository
import com.example.myquestquiz_1.MyDatabase.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class QuestPageViewModel(application: Application): AndroidViewModel(application) {
}