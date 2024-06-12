package com.example.myquestquiz_1.SettingActivity

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

class SettingActivityViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var myRepository: MyRepository
    private lateinit var SettingActivityVM_InitJob: Job
    var questionTotl = MutableLiveData<Int>()
    var numInput = MutableLiveData<Int>()
    var shuffledTitleSwitch = MutableLiveData<Boolean>()
    var shuffledQuestionSwitch = MutableLiveData<Boolean>()
    var questionsListNow = MutableLiveData<List<Question>>()
    var selectedBank = MutableLiveData<Long>()
    var bankName = MutableLiveData<String>()

    init {
        SettingActivityVM_InitJob = CoroutineScope(Dispatchers.Main).launch {
            myRepository = MyRepository(application)
            joinAll(myRepository.getMyRepositoryInitJob())
            selectedBank.value = 0
            questionTotl.value = 0
            numInput.value = 0
            shuffledTitleSwitch.value = true
            shuffledQuestionSwitch.value = true
            questionsListNow.value = null
        }
    }
    //從儲存庫中更新VM中的questionsListNow
    fun updateQuestionsListNow(questionBelong: Long): Job {
        var updateQuestionsListNow_Job = CoroutineScope(Dispatchers.Main).launch {
            with(myRepository) {
                joinAll(updateQuestions_ByQuestionqBelong(questionBelong))
                questionsListNow.value = getListOfQuestions()
            }
        }
        return updateQuestionsListNow_Job
    }

    //更新VM中的questionTotl
    fun updateQuestionTotl(questionList: List<Question>?) {
        questionTotl.value = questionList?.size
    }
    //取得VM的initJob
    fun getVMinitJob(): Job{
        return SettingActivityVM_InitJob
    }

    fun deleteQuestionByID(questionID: Long, questionBelong: Long){
        CoroutineScope(Dispatchers.Main).launch {
            joinAll(myRepository.deleteQuestionByID(questionID))
            joinAll(updateQuestionsListNow(questionBelong))
        }
    }

}