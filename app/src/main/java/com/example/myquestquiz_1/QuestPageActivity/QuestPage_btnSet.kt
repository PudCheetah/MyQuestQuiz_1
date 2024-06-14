package com.example.myquestquiz_1.QuestPageActivity

import QuestPageViewModel
import android.content.Context
import android.widget.Toast
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.databinding.ActivityQuestPageBinding

class QuestPage_btnSet(var context: Context, var binding: ActivityQuestPageBinding, var myVM: QuestPageViewModel, var intentManager: IntentManager, var questPage_AlertDialogSet: QuestPage_AlertDialogSet) {
    fun btn1ToNextQuestion_set(){
        binding.btn1ToNextQuestion.setOnClickListener {
            if (myVM.numExpect_intent.value!! > (myVM.progressControler.value!! + 1)){
                myVM.progressControler.value = myVM.progressControler.value!!.plus(1)
                myVM.isHadAns.value = false
                binding.root.invalidate()
            }else if(myVM.numExpect_intent.value!! == (myVM.progressControler.value!! + 1)){
//                if (myVM.isHadAns.value == false){
//                    Toast.makeText(it.context, "您尚未做答本題", Toast.LENGTH_SHORT).show()
//                }else{
                    Toast.makeText(context, "已經到底了", Toast.LENGTH_SHORT).show()
                    intentManager.putExtra("ToResult", "scoreCounter", myVM.scoreCounter.value)
                    questPage_AlertDialogSet.alertDialogSet()
//                }
            }else{
//                Toast.makeText(context, "已經到底了", Toast.LENGTH_SHORT).show()
//                intentManager.putExtra("ToResult", "scoreCounter", myVM.scoreCounter.value)
//                questPage_AlertDialogSet.alertDialogSet()
            }
        }
    }
}