package com.example.myquestquiz_1.QuestPageActivity

import QuestPageViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.MyDatabase.Question
import com.example.myquestquiz_1.databinding.ActivityQuestPageBinding

class QuestPage_RV_AdapterSet(var binding: ActivityQuestPageBinding, var myVM: QuestPageViewModel, var intentManager: IntentManager) {
    fun rvAdapterSet(question: Question){
        with(binding.RV1ForOption){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = QuestPage_RV_Adapter(myVM, question, intentManager)
        }
    }
}