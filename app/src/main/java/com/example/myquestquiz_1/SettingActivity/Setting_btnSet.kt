package com.example.myquestquiz_1.SettingActivity

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.Manager.KeyboardManager
import com.example.myquestquiz_1.databinding.ActivitySettingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class Setting_btnSet(
    var context: Context,
    var binding: ActivitySettingBinding,
    var myVM: SettingActivityViewModel,
    var intentManager: IntentManager,
    var toastManager: Setting_toastManager
) {
    //開始測驗按鈕行為
    fun btn1ToQuestPage_set() {
        binding.btn1ToQuestPage.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                joinAll(myVM.updateCountOfQuestionByQuestionqBelong())
                if (myVM.countOfQuestionByQuestionqBelong.value!!.toInt() == 0) {
                    Toast.makeText(context, "錯誤，題目為零，請先新增題目", Toast.LENGTH_SHORT).show()
                } else {
                    if (binding.numET1TypeNumOfQuestion.text.isNullOrEmpty()) {
                        myVM.numInput.value = myVM.questionsListNow.value?.size
                    } else {
                        myVM.numInput.value = binding.numET1TypeNumOfQuestion.text.toString().toInt()
                    }
                    intentManager.putExtra("ToQuestPage", "numExpect", myVM.numInput.value)
                    intentManager.putExtra("ToQuestPage", "bankID", myVM.selectedBank.value)
                    intentManager.putExtra(
                        "ToQuestPage",
                        "ShuffledTitleSwitch",
                        myVM.shuffledTitleSwitch.value
                    )
                    intentManager.putExtra(
                        "ToQuestPage",
                        "shuffledOption",
                        myVM.shuffledOptionSwitch.value
                    )
                    startActivity(context, intentManager.getIntent("ToQuestPage")!!, null)
                }
            }
            }
    }

    //新增題目按鈕行為
    fun btn3ToAddQuestionPage_set() {
        binding.btn3ToAddQuestionPage.setOnClickListener {
            intentManager.putExtra("ToAddQuestion", "SelectedBank", myVM.selectedBank.value)
            intentManager.putExtra("ToAddQuestion", "BankName", myVM.bankName.value)
            startActivity(context, intentManager.getIntent("ToAddQuestion")!!, null)
        }
    }

    fun btn2NumOfQuestionConfirm_set() {
        binding.btn2NumOfQuestionConfirm.setOnClickListener {
            binding.numET1TypeNumOfQuestion.clearFocus()
            var keyboardManager = KeyboardManager()
            keyboardManager.hideKeyBoard(it)
            toastManager.toastManager("btn2NumOfQuestionConfirm", context)
        }
    }
}