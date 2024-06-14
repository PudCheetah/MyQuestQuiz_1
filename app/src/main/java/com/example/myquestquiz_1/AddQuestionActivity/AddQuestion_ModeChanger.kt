package com.example.myquestquiz_1.AddQuestionActivity

import androidx.core.view.isInvisible
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding

class AddQuestion_ModeChanger(var binding: ActivityAddNewQuestionBinding) {
    //將activity轉換成此Activity的模式
    fun changecAtivity_toAddNewQuestionMode(){
        binding.TV7QuestionTitle.isInvisible = true
        binding.TV6QuestionID.isInvisible = true
        binding.TV8CorrectAnsNum.isInvisible = true
    }
}