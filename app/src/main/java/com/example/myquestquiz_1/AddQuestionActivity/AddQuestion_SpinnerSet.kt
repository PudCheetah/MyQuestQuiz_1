package com.example.myquestquiz_1.AddQuestionActivity

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myquestquiz_1.R
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding

class AddQuestion_SpinnerSet(var context: Context, var binding: ActivityAddNewQuestionBinding, var myVM: AddNewQuestionViewModel) {
    //Spinner設置1
    fun spinnerAdapterSet_ForNumOfOption(){
        var myResource = context.resources.getStringArray(R.array.activityNewQuestionSpinner)
        var mySpinnerAdapter = ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, myResource)
        mySpinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.spinner1ForNumOfOption.adapter = mySpinnerAdapter
        binding.spinner1ForNumOfOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                myVM.spinnerChoice.value = position
                myVM.clearQuestionInfo()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    //Spinner設置2
    fun spinnerAdapterSet_ForCorrectAns(){
        var myResource = (0..myVM.spinnerChoice.value!!).toList()
        var mySpinnerAdapter = ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, myResource)
        binding.spinner2ForCorrectAns.adapter = mySpinnerAdapter
        binding.spinner2ForCorrectAns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                myVM.correctAns.value = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}