package com.example.myquestquiz_1.QuestPageActivity

import QuestPageViewModel
import android.content.Context
import android.util.Log
import com.example.myquestquiz_1.R
import com.example.myquestquiz_1.databinding.ActivityQuestPageBinding

class QuestPage_ObserveSet(
    var context: Context,
    var binding: ActivityQuestPageBinding,
    var myVM: QuestPageViewModel,
    var questpageRvAdapterset: QuestPage_RV_AdapterSet,
) {
    fun progressControler_ObserveSet() {
        //numInSeccondOrderList代表亂數列表中的第(${myVM.progressControler.value}+1)個數字
        var numInSeccondOrderList =
            myVM.seccondOrderList.value!!.get(myVM.progressControler.value!!)
        binding.TV2QuetionTitle.text =
            myVM.listOfQuestions.value!![numInSeccondOrderList].questionTitle
        binding.TV4ProgressNow.text = (myVM.progressControler.value!! + 1).toString()
        questpageRvAdapterset.rvAdapterSet(myVM.listOfQuestions.value!![numInSeccondOrderList])
        if (myVM.numExpect_intent.value!! == (myVM.progressControler.value!! + 1)){
            binding.btn1ToNextQuestion.setText(context.getString(R.string.Result))
        }
    }
}
