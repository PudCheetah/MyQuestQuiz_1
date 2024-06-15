package com.example.myquestquiz_1.AddQuestionActivity

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding

class AddNewQuestion_RV_AdapterSet(var context: Context, var binding: ActivityAddNewQuestionBinding, var myVM: AddNewQuestionViewModel) {
    //Adapter設置
    fun rvAdapterSet(){
        with(binding.RV1ForOption){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = AddNewQuestion_RV_Adapter(myVM)
        }
    }
}