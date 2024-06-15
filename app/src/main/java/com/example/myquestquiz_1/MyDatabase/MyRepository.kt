package com.example.myquestquiz_1.MyDatabase

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MyRepository(context: Context) {


    companion object{
        private var myDao: MyQuestionDao? = null
        private lateinit var myRepositoryInitJob: Job
        private var myQuestion_inMyRepository: Question? = null
        private var listOfQuestions_inMyRepository: List<Question>? = null
        private var listOfBank_inMyRepository: List<MyQuestionBank>? = null
    }
    private var CountOfQuestionByQuestionqBelong: Long ?= 0

    init {
        myRepositoryInitJob = CoroutineScope(Dispatchers.IO).launch {
            myDao = MyDatabase?.getInstance(context)?.myQuestionDao()
//            listOfBank = myDao?.getAllQuestionBank()
        }
    }

    //更新Question列表
    fun updateQuestions_ByQuestionqBelong(questionqBelong: Long): Job {
        var updateQuestionsJob = CoroutineScope(Dispatchers.IO).launch {
            listOfQuestions_inMyRepository = myDao?.getAllQuestionByQuestionqBelong(questionqBelong)
        }
        return updateQuestionsJob
    }
    //獲得Question列表
    fun getListOfQuestions(): List<Question>?{
        return listOfQuestions_inMyRepository
    }

    //更新QuestionDetail by QuestionID
    fun updateMyQuestion_inMyRepository(questionID: Long): Job{
        var updateMyQuestionByQuestionIDJob = CoroutineScope(Dispatchers.IO).launch {
            myQuestion_inMyRepository = myDao?.getQuestionByID(questionID)
        }
        return updateMyQuestionByQuestionIDJob
    }
    fun getMyQuestionFromMyRepository(): Question?{
        return myQuestion_inMyRepository
    }

    //獲取題庫列表
    fun getListOfBank(): List<MyQuestionBank>? {
        return listOfBank_inMyRepository
    }

    //從資料庫中取得資料來更新MyRepository中的Question列表
    fun updateQuestions(questionqBelong: Long): Job{
        var updateQuestionsJob = CoroutineScope(Dispatchers.IO).launch {
            listOfQuestions_inMyRepository = myDao?.getAllQuestionByQuestionqBelong(questionqBelong)
        }
        return updateQuestionsJob
    }
    //從資料庫中取得資料來更新MyRepository中的題庫列表
    fun updateListOfBank(): Job{
        var updateListOfBankJob = CoroutineScope(Dispatchers.IO).launch {
            listOfBank_inMyRepository = myDao?.getAllQuestionBank()
        }
        return updateListOfBankJob
    }

    //獲取MyRepository的初始化工作
    fun getMyRepositoryInitJob(): Job {
        return myRepositoryInitJob
    }

    //插入新的題庫到資料庫,並返回Job
    fun upsertBank(string: String): Job {
        var upsertBankJob = CoroutineScope(Dispatchers.IO).launch {
            myDao?.upsertQuestionBank(MyQuestionBank(questionBankName = string))
        }
        return upsertBankJob
    }

    //插入或更新Question到資料庫，並返回Job
    fun upsertQuestion(
        questionqBelong: Long,
        questionTitle: String,
        questionID: Long? = null,
        ans1: String? = null,
        ans2: String? = null,
        ans3: String? = null,
        ans4: String? = null,
        ans5: String? = null,
        ans6: String? = null,
        ans7: String? = null,
        ans8: String? = null,
        ans9: String? = null,
        ans10: String? = null,
        correctAns: Int
    ): Job {
        var myQuestionInfo = Question(
            questionqBelong,
            questionID,
            questionTitle,
            ans1,
            ans2,
            ans3,
            ans4,
            ans5,
            ans6,
            ans7,
            ans8,
            ans9,
            ans10,
            correctAns
        )
        var upsertQuestionJob = CoroutineScope(Dispatchers.IO).launch {
            myDao?.upsertQuestion(myQuestionInfo)
        }
        return upsertQuestionJob
    }
    //插入新的Question到資料庫並返回Job
    fun upsertQuestion(questionqBelong: Long, question: Question): Job{
        var upsertQuestionJob = CoroutineScope(Dispatchers.IO).launch {
            myDao?.upsertQuestion(question)
        }
        return upsertQuestionJob
    }

    fun updateQuestion(question: Question): Job{
        var updateQuestionJob = CoroutineScope(Dispatchers.IO).launch {
            myDao?.updateQuestion(question)
        }
        return updateQuestionJob
    }


    //刪除指定的題庫
    fun deleteQuestionBankById(questionBankId: Long): Job{
        var deleteQuestionBankByIdJob =CoroutineScope(Dispatchers.IO).launch {
            myDao?.deleteQuestionBankById(questionBankId)
        }
        return deleteQuestionBankByIdJob
    }
    fun deleteQuestionByID(questionID: Long): Job{
        var deleteQuestionJob = CoroutineScope(Dispatchers.IO).launch {
            myDao?.deleteQuestionByID(questionID)
        }
        return deleteQuestionJob
    }
    //根據questionBelong查詢該題庫有多少題目
    fun getCountOfQuestionByQuestionqBelong(questionqBelong: Long): Job{
        var getCountJob = CoroutineScope(Dispatchers.IO).launch {
            CountOfQuestionByQuestionqBelong = myDao?.getCountOfQuestionByQuestionqBelong(questionqBelong)
        }
        return getCountJob
    }
    fun getCountOfQuestionInMyRepository(): Long?{
        return CountOfQuestionByQuestionqBelong ?: 0
    }

    fun deleteQuestionByByQuestionBelong(questionBankId: Long): Job{
        var deleteQuestionByQuestionBelongJob = CoroutineScope(Dispatchers.IO).launch {
            myDao?.deleteQuestionByQuestionBelong(questionBankId)
        }
        return deleteQuestionByQuestionBelongJob
    }
}