package com.example.myquestquiz_1.QuestionInfo

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
    var option1 = MutableLiveData<String?>()
    var option2 = MutableLiveData<String?>()
    var option3 = MutableLiveData<String?>()
    var option4 = MutableLiveData<String?>()
    var option5 = MutableLiveData<String?>()
    var option6 = MutableLiveData<String?>()
    var option7 = MutableLiveData<String?>()
    var option8 = MutableLiveData<String?>()
    var option9 = MutableLiveData<String?>()
    var option10 = MutableLiveData<String?>()
    var correctAns = MutableLiveData<Int>()
    var optionList = MutableLiveData<List<MutableLiveData<String?>>>()

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
            option1.value = questionNow.value!!.ans1
            option2.value = questionNow.value!!.ans2
            option3.value =  questionNow.value!!.ans3
            option4.value = questionNow.value!!.ans4
            option5.value = questionNow.value!!.ans5
            option6.value = questionNow.value!!.ans6
            option7.value = questionNow.value!!.ans7
            option8.value = questionNow.value!!.ans8
            option9.value = questionNow.value!!.ans9
            option10.value = questionNow.value!!.ans10
            correctAns.value = questionNow.value!!.correctAns
            optionList.value = listOf(option1, option2, option3, option4, option5, option6, option7, option8, option9, option10)
        }
    }

    fun getVM_initJob(): Job{
        return questionInfoViewModelInitJob
    }
    //以VM中的數據更新QuestionNow
    fun updateQuestionNowWithValueInVM(){
        var newQuestion = Question(
            questionqBelong.value,
            questionID.value,
            questionTitle.value,
            option1.value,
            option2.value,
            option3.value,
            option4.value,
            option5.value,
            option6.value,
            option7.value,
            option8.value,
            option9.value,
            option10.value,
            correctAns.value
        )
        questionNow.value = newQuestion
        Log.d("myTag_VM", "questionNow has update: ${questionNow.value}")
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

    //依照position將value放進VM的option中
    fun putValueToVM(position: Int, value: String?){
        var optionList = listOf(option1, option2, option3, option4, option5, option6, option7, option8, option9, option10)
        optionList[position].value = value
    }
    //依照position拿取VM中儲存的option
    fun getValueFromVM(position: Int): String?{
        var optionList = listOf(option1, option2, option3, option4, option5, option6, option7, option8, option9, option10)
        return optionList[position].value
    }

    //將questionNow放到資料庫中
    fun updateQuestionNowToDB(): Job{
        var updateQuestionNowToDB_Job = CoroutineScope(Dispatchers.Main).launch {
            joinAll(myRepository.updateQuestion(questionNow.value!!))
        }
        return updateQuestionNowToDB_Job
    }
}