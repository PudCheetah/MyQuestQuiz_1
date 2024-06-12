package com.example.myquestquiz_1.SettingActivity

import android.content.Context
import androidx.core.content.ContextCompat.startActivity
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.Manager.KeyboardManager
import com.example.myquestquiz_1.databinding.ActivitySettingBinding

class Setting_btnSet(var context: Context, var binding: ActivitySettingBinding,var myVM: SettingActivityViewModel, var intentManager: IntentManager, var toastManager: Setting_toastManager) {
    //開始測驗按鈕行為
    fun btn1ToQuestPage_set(numInput: Int?){
        binding.btn1ToQuestPage.setOnClickListener {
            intentManager.putExtra("ToQuestPage", "numExpect", numInput)
            startActivity(context, intentManager.getIntent("ToQuestPage")!!, null)
        }
    }
    //新增題目按鈕行為
    fun btn3ToAddQuestionPage_set(){
        binding.btn3ToAddQuestionPage.setOnClickListener {
            intentManager.putExtra("ToAddQuestion", "SelectedBank",  myVM.selectedBank.value)
            intentManager.putExtra("ToAddQuestion", "BankName",  myVM.bankName.value)
            startActivity(context, intentManager.getIntent("ToAddQuestion")!!, null)
        }
    }
    fun btn2NumOfQuestionConfirm_set(){
        binding.btn2NumOfQuestionConfirm.setOnClickListener {
            binding.numET1TypeNumOfQuestion.clearFocus()
            var keyboardManager = KeyboardManager()
            keyboardManager.hideKeyBoard(it)
            toastManager.toastManager("btn2NumOfQuestionConfirm", context)
        }
    }
}