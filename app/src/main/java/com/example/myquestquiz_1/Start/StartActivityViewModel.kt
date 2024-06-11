package com.example.myquestquiz_1.Start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myquestquiz_1.MyDatabase.MyQuestionBank
import com.example.myquestquiz_1.MyDatabase.MyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartActivityViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var myRepository: MyRepository
    private lateinit var startActivityViewModel_InitJob: Job
    var lisOfBank = MutableLiveData<List<MyQuestionBank>>()

    init {
        startActivityViewModel_InitJob = CoroutineScope(Dispatchers.Main).launch {
            myRepository = MyRepository(application)
            joinAll(myRepository.getMyRepositoryInitJob())
            joinAll(myRepository.updateListOfBank())
            lisOfBank.value = myRepository.getListOfBank()
        }
    }
    fun getStartActivityViewModel_InitJob(): Job{
        return startActivityViewModel_InitJob
    }


    //插入新的題庫到資料庫中，並更新MyRepository中的題庫列表，並返回Job
    fun upsertBank(string: String): Job{
        var myVM_upsertBankJob = CoroutineScope(Dispatchers.IO).launch{
            joinAll(myRepository.upsertBank(string))
            joinAll(myRepository.updateListOfBank())
            withContext(Dispatchers.Main){
                lisOfBank.value = (myRepository.getListOfBank())
            }

        }
        return myVM_upsertBankJob
    }

    //刪除指定題庫
    fun deleteQuestionBankById(questionBankId: Long): Job{
        var myVm_DeleteQuestionBankByIdJob = CoroutineScope(Dispatchers.IO).launch {
            joinAll(myRepository.deleteQuestionBankById(questionBankId))
            joinAll(myRepository.updateListOfBank())
            lisOfBank.postValue(myRepository.getListOfBank())
        }
        return myVm_DeleteQuestionBankByIdJob
    }
}