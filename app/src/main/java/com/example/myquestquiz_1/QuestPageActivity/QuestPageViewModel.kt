//package com.example.myquestquiz_1.QuestPageActivity
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.MutableLiveData
//import com.example.myquestquiz_1.MyDatabase.MyRepository
//import com.example.myquestquiz_1.MyDatabase.Question
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
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

class QuestPageViewModel(application: Application, bankID: Long, numExpect: Int): AndroidViewModel(application) {
    private lateinit var myRepository: MyRepository
    private lateinit var VM_initJob: Job

    //用來控制題號的串列，大小為(1~ numExpect_int)
    var outSideList = MutableLiveData<List<Int>>()
    //用來控制要從listOfQuestion
    var seccondOrderList = MutableLiveData<List<Int>>()
    //此bank中的所有題目的串列
    var listOfQuestions = MutableLiveData<List<Question>>()

    var progressControler = MutableLiveData<Int>()
    var numExpect_intent = MutableLiveData<Int>()
    var bankID_intent = MutableLiveData<Long>()
    var shuffledTitleSwitch_intent = MutableLiveData<Boolean>()
    var shuffledOption_intent = MutableLiveData<Boolean>()
    var scoreCounter = MutableLiveData<Double>()

    init {
        bankID_intent.value = bankID
        numExpect_intent.value = numExpect
        VM_initJob = CoroutineScope(Dispatchers.Main).launch {
            myRepository = MyRepository(application)
            joinAll(myRepository.getMyRepositoryInitJob())
            myRepository.updateQuestions_ByQuestionqBelong(bankID_intent.value!!)
            listOfQuestions.value = myRepository.getListOfQuestions()
            progressControler.value = 0
        }
    }

    fun getVM_initJob(): Job{
        return VM_initJob
    }
    fun optionProvider(question: Question, position: Int): String?{
        return when(position){
            0 -> {question.ans1}
            1 -> {question.ans2}
            2 -> {question.ans3}
            3 -> {question.ans4}
            4 -> {question.ans5}
            5 -> {question.ans6}
            6 -> {question.ans7}
            7 -> {question.ans8}
            8 -> {question.ans9}
            9 -> {question.ans10}
            else -> {"optionProvider gones wrong"}
        }
    }

}