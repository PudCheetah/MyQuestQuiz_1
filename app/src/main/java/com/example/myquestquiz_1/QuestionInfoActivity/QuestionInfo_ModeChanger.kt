package com.example.myquestquiz_1.QuestionInfoActivity

import androidx.core.view.isInvisible
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding

class QuestionInfo_ModeChanger(var binding: ActivityAddNewQuestionBinding, var myVM: QuestionInfoViewModel, var RV_adapterSet: QuestionInfo_RV_AdapterSet) {
    //切換介面到info模式
    fun changecAtivity_toInfoMode() {
        with(binding) {
            TV7QuestionTitle.isInvisible = false
            ET1ForQuestionTyping.isInvisible = true
            TV2ForNumOfOptionText.isInvisible = true
            spinner1ForNumOfOption.isInvisible = true
            btn1QuestionInputConfirm.isInvisible = true
//            TV3CorrectAnsText.isInvisible = true
            spinner1ForNumOfOption.isInvisible = true
            spinner2ForCorrectAns.isInvisible = true
            TV8CorrectAnsNum.isInvisible = false
            RV_adapterSet. RV_adapterSet("infoMode")
            btn2Store.setText("編輯")
            TV6QuestionID.text = ("題目ID: " + "${myVM.questionNow.value!!.questionID}")
            TV7QuestionTitle.text = ("${myVM.questionNow.value!!.questionTitle}")
            binding.TV8CorrectAnsNum.text = myVM.questionNow.value!!.correctAns.toString()
            root.invalidate()
        }
    }
    //切換模式到編輯模式
    fun changeActivity_toEditMode() {
        with(binding) {
            TV7QuestionTitle.isInvisible = true
            ET1ForQuestionTyping.isInvisible = false
            TV2ForNumOfOptionText.isInvisible = false
            spinner1ForNumOfOption.isInvisible = false
            btn1QuestionInputConfirm.isInvisible = false
            spinner1ForNumOfOption.isInvisible = false
            spinner2ForCorrectAns.isInvisible = false
            TV8CorrectAnsNum.isInvisible = true
            binding.TV7QuestionTitle.text = ("${myVM.questionNow.value!!.questionTitle}")
            ET1ForQuestionTyping.setText(myVM.questionTitle.value.toString())
            spinner1ForNumOfOption.setSelection(myVM.numOfOptionInQuestionNow.value!!)
            spinner2ForCorrectAns.setSelection(myVM.questionNow.value!!.correctAns!!)
            RV_adapterSet.RV_adapterSet("editMode")
            btn2Store.setText("儲存")
            binding.TV8CorrectAnsNum.text = myVM.questionNow.value!!.correctAns.toString()
            root.invalidate()
        }
    }
}