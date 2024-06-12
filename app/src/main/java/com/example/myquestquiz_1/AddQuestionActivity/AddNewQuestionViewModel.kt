package com.example.myquestquiz_1.AddQuestionActivity

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


class AddNewQuestionViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var addNewQuestionViewModel_InitJob: Job
    private lateinit var myRepository: MyRepository
    var spinnerChoice = MutableLiveData<Int>()
    var selectedBank = MutableLiveData<Long>()
    var bankNmae = MutableLiveData<String>()
    var questionTitleInput = MutableLiveData<String>()
    var choice_1 = MutableLiveData<String>()
    var choice_2 = MutableLiveData<String>()
    var choice_3 = MutableLiveData<String>()
    var choice_4 = MutableLiveData<String>()
    var choice_5 = MutableLiveData<String>()
    var choice_6 = MutableLiveData<String>()
    var choice_7 = MutableLiveData<String>()
    var choice_8 = MutableLiveData<String>()
    var choice_9 = MutableLiveData<String>()
    var choice_10 = MutableLiveData<String>()
    var correctAns = MutableLiveData<Int>()
    var myQuestion = MutableLiveData<Question>()

    init {
        addNewQuestionViewModel_InitJob = CoroutineScope(Dispatchers.Main).launch {
            myRepository = MyRepository(application)
            joinAll(myRepository.getMyRepositoryInitJob())
            joinAll(myRepository.updateListOfBank())
            spinnerChoice.value = 0
            selectedBank.value = 0
            questionTitleInput.value = ""
            choice_1.value = null
            choice_2.value = null
            choice_3.value = null
            choice_4.value = null
            choice_5.value = null
            choice_6.value = null
            choice_7.value = null
            choice_8.value = null
            choice_9.value = null
            choice_10.value = null
        }
    }

    //按照position將字串放進VN中
    fun putStringToVM(string: String?, position: Int) {
        when (position) {
            0 -> {
                choice_1.value = string
            }

            1 -> {
                choice_2.value = string
            }

            2 -> {
                choice_3.value = string
            }

            3 -> {
                choice_4.value = string
            }

            4 -> {
                choice_5.value = string
            }

            5 -> {
                choice_6.value = string
            }

            6 -> {
                choice_7.value = string
            }

            7 -> {
                choice_8.value = string
            }

            8 -> {
                choice_9.value = string
            }

            9 -> {
                choice_10.value = string
            }
        }
    }

    fun packageToQuestion() {
        var myNewQuestion = Question(
            QuestionqBelong = selectedBank.value!!,
            questionID = null,
            questionTitle = questionTitleInput.value!!,
            ans1 = choice_1.value,
            ans2 = choice_2.value,
            ans3 = choice_3.value,
            ans4 = choice_4.value,
            ans5 = choice_5.value,
            ans6 = choice_6.value,
            ans7 = choice_7.value,
            ans8 = choice_8.value,
            ans9 = choice_9.value,
            ans10 = choice_10.value,
            correctAns = correctAns.value!!
        )
        myQuestion.value = myNewQuestion
    }

    fun updateMyQuestionByQuestion(question: Question) {
        myQuestion.value = question
    }


    //將Question加入Database
    fun storeQuestionToDatabase(application: Application, question: Question): Job {
        var storeQuestionToDatabaseJob = CoroutineScope(Dispatchers.Main).launch {
            joinAll(
                myRepository.upsertQuestion(
                    questionqBelong = selectedBank.value!!,
                    question
                )
            )
        }
        return storeQuestionToDatabaseJob
    }

    //清空VM中的question資料
    fun clearQuestionInfo() {
        questionTitleInput.value = ""
        choice_1.value = null
        choice_2.value = null
        choice_3.value = null
        choice_4.value = null
        choice_5.value = null
        choice_6.value = null
        choice_7.value = null
        choice_8.value = null
        choice_9.value = null
        choice_10.value = null
    }


    fun getVMinitJob(): Job {
        return addNewQuestionViewModel_InitJob
    }
}