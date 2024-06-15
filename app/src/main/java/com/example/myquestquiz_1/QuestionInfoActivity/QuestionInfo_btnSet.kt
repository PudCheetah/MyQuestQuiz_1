package com.example.myquestquiz_1.QuestionInfoActivity

import com.example.myquestquiz_1.Manager.KeyboardManager
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding

class QuestionInfo_btnSet(
    var binding: ActivityAddNewQuestionBinding,
    var myVM: QuestionInfoViewModel,
    var modeChanger: QuestionInfo_ModeChanger,
    var functionIntegrate: QuestionInfo_FunctionIntegrate,
    var keyboardManager: KeyboardManager
) {

    fun btn1QuestionInputConfirm_set() {
        binding.btn1QuestionInputConfirm.setOnClickListener {
            myVM.questionNow.value!!.questionTitle =
                binding.ET1ForQuestionTyping.text.toString()
            binding.ET1ForQuestionTyping.clearFocus()
            keyboardManager.hideKeyBoard(it)
        }
    }

    fun btn2Store_set() {
        binding.btn2Store.setOnClickListener {
            if (myVM.storeAccept.value == true) {
                modeChanger.changeActivity_toEditMode()
            } else {
                modeChanger.changecAtivity_toInfoMode()
                functionIntegrate.stroeToDB()
            }
        }
    }
}