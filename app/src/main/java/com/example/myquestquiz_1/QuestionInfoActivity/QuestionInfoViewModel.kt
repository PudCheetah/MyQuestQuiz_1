package com.example.myquestquiz_1.QuestionInfoActivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myquestquiz_1.Manager.QuestionsOptionManager
import com.example.myquestquiz_1.MyDatabase.MyRepository
import com.example.myquestquiz_1.MyDatabase.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class QuestionInfoViewModel(application: Application, var questionIDFromIntent: Long) : AndroidViewModel(application) {
    private lateinit var myRepository: MyRepository
    private lateinit var questionsOptionManager: QuestionsOptionManager
    private lateinit var questionInfoViewModelInitJob: Job
    var questionNow = MutableLiveData<Question>()
    var numOfOptionInQuestionNow = MutableLiveData<Int>()
    var questionqBelong = MutableLiveData<Long>()
    var bankName = MutableLiveData<String>()
    var questionID = MutableLiveData<Long>()
    var questionTitle =MutableLiveData<String>()
    var correctAns = MutableLiveData<Int>()
    var storeAccept = MutableLiveData<Boolean>()

    init {
        questionInfoViewModelInitJob = CoroutineScope(Dispatchers.Main).launch {
            questionsOptionManager = QuestionsOptionManager()
            myRepository = MyRepository(application)
            questionID.value = questionIDFromIntent
            joinAll(myRepository.updateMyQuestion_inMyRepository(questionID.value!!))
            questionNow.value = myRepository.getMyQuestionFromMyRepository()!!
            numOfOptionInQuestionNow.value = questionsOptionManager.getNotNullAnsTotal(questionNow.value!!)
            questionqBelong.value = questionNow.value!!.QuestionqBelong
            questionTitle.value = questionNow.value!!.questionTitle
            correctAns.value = questionNow.value!!.correctAns
        }
    }

    fun getVM_initJob(): Job{
        return questionInfoViewModelInitJob
    }
    fun getValueFromQuestionNow(position: Int): String?{
        return when(position){
            0 -> {questionNow.value!!.ans1}
            1 -> {questionNow.value!!.ans2}
            2 -> {questionNow.value!!.ans3}
            3 -> {questionNow.value!!.ans4}
            4 -> {questionNow.value!!.ans5}
            5 -> {questionNow.value!!.ans6}
            6 -> {questionNow.value!!.ans7}
            7 -> {questionNow.value!!.ans8}
            8 -> {questionNow.value!!.ans9}
            9 -> {questionNow.value!!.ans10}
            else -> {"Nothing here"}
        }
    }
    fun putValueToQuestionNow(position: Int, value: String?){
        when(position){
            0 -> {questionNow.value!!.ans1 = value}
            1 -> {questionNow.value!!.ans2 = value}
            2 -> {questionNow.value!!.ans3 = value}
            3 -> {questionNow.value!!.ans4 = value}
            4 -> {questionNow.value!!.ans5 = value}
            5 -> {questionNow.value!!.ans6 = value}
            6 -> {questionNow.value!!.ans7 = value}
            7 -> {questionNow.value!!.ans8 = value}
            8 -> {questionNow.value!!.ans9 = value}
            9 -> {questionNow.value!!.ans10 = value}
            else -> {"Nothing here"}
        }
    }

    //將questionNow放到資料庫中
    fun updateQuestionNowToDB(): Job{
        var updateQuestionNowToDB_Job = CoroutineScope(Dispatchers.Main).launch {
            joinAll(myRepository.updateQuestion(questionNow.value!!))
        }
        return updateQuestionNowToDB_Job
    }
}