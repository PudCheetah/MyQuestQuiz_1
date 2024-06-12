package com.example.myquestquiz_1.AddQuestionActivity

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.window.application
import com.example.myquestquiz_1.Manager.KeyboardManager
import com.example.myquestquiz_1.Manager.QuestionsOptionManager
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class AddQuestion_btnSet(var context: Context, var binding: ActivityAddNewQuestionBinding, var myVM: AddNewQuestionViewModel, var application: Application) {
    //題目按鈕的行為
    fun btn1QuestionInputConfirm_set(){
        var keyboardManager = KeyboardManager()
        binding.btn1QuestionInputConfirm.setOnClickListener {
            binding.RV1ForOption.clearFocus()
            keyboardManager.hideKeyBoard(binding.root)
            binding.ET1ForQuestionTyping.clearFocus()
            binding.root.requestFocus()
            if (binding.ET1ForQuestionTyping.text.isEmpty()){
                Toast.makeText(context, "錯誤，題目不可為空", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "題目輸入完成", Toast.LENGTH_SHORT).show()
                myVM.questionTitleInput.value = binding.ET1ForQuestionTyping.text.toString()
            }
        }
    }
    //儲存按鈕的行為
    fun btn2Store_set(){
        var questionsOptionManager = QuestionsOptionManager()
        binding.btn2Store.setOnClickListener {
            if (binding.ET1ForQuestionTyping.text.isEmpty()){
                Toast.makeText(context, "錯誤，題目不可為空", Toast.LENGTH_SHORT).show()
            }else{
                CoroutineScope(Dispatchers.Main).launch {
                    myVM.questionTitleInput.value = binding.ET1ForQuestionTyping.text.toString()
                    myVM.packageToQuestion()
                    var newQuestion = questionsOptionManager.optionSortByNonNull(myVM.myQuestion.value!!)
                    myVM.updateMyQuestionByQuestion(newQuestion)
                    joinAll(myVM.storeQuestionToDatabase(application, myVM.myQuestion.value!!))
                    Toast.makeText(application, "儲存完成，可返回上一頁或繼續新增", Toast.LENGTH_SHORT).show()
                    binding.ET1ForQuestionTyping.setText("")
                    myVM.clearQuestionInfo()
                    binding.spinner1ForNumOfOption.setSelection(0)
                    binding.spinner2ForCorrectAns.setSelection(0)
                }
            }
        }
    }

}