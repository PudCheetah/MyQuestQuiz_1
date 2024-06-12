package com.example.myquestquiz_1.QuestionInfoActivity

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myquestquiz_1.R
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding

class QuestionInfo_SpinnerSet(var context: Context, var binding: ActivityAddNewQuestionBinding, var myVM: QuestionInfoViewModel) {
    //選擇選項數的下拉式選單設定
    fun spinnerAdapterSet_ForNumOfOption() {
        var myResource = context.resources.getStringArray(R.array.activityNewQuestionSpinner)
        var mySpinnerAdapter = ArrayAdapter(
            context,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            myResource
        )
        mySpinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.spinner1ForNumOfOption.adapter = mySpinnerAdapter
    }
    //ForNumOfOption的監聽器
    fun spinnerAdapterSet_ForNumOfOption_selectListener(){
        binding.spinner1ForNumOfOption.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if(myVM.numOfOptionInQuestionNow.value != position){
                        myVM.numOfOptionInQuestionNow.value = position
                    }
                    Log.d(
                        "myTag",
                        "myVM.numOfOptionInQuestionNow.value---inSpinner ${myVM.numOfOptionInQuestionNow.value}"
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }
    //選擇正確答案的下拉式選單設定
    fun spinnerAdapterSet_ForCorrectAns() {
        var myResource: List<Int>
        if (myVM.questionNow.value!!.correctAns!! > myVM.numOfOptionInQuestionNow.value!!) {
            myResource = (0..myVM.questionNow.value!!.correctAns!!).toList()
        } else {
            myResource = (0..myVM.numOfOptionInQuestionNow.value!!).toList()
        }
        var mySpinnerAdapter = ArrayAdapter(
            context,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            myResource
        )
        binding.spinner2ForCorrectAns.adapter = mySpinnerAdapter

    }
    //ForNumOfOption的監聽器

    fun spinnerAdapterSet_ForCorrectAns_selectListener(){
        binding.spinner2ForCorrectAns.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if(myVM.questionNow.value!!.correctAns != position){
                        myVM.questionNow.value!!.correctAns = position
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }
}