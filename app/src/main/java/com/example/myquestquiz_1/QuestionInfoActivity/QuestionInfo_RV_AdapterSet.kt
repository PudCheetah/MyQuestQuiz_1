package com.example.myquestquiz_1.QuestionInfoActivity

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquestquiz_1.RVadapter.QuestionInfoEdit_RV_adapter
import com.example.myquestquiz_1.RVadapter.QuestionInfo_RV_adapter
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding

class QuestionInfo_RV_AdapterSet(var context: Context,var binding: ActivityAddNewQuestionBinding, var myVM: QuestionInfoViewModel) {
    //RV的adapter設定

    fun RV_adapterSet(string: String) {
        binding.RV1ForOption.layoutManager = LinearLayoutManager(context)
        binding.RV1ForOption.setHasFixedSize(true)
        when (string) {
            "infoMode" -> {
                binding.RV1ForOption.adapter = QuestionInfo_RV_adapter(myVM)
            }

            "editMode" -> {
                binding.RV1ForOption.adapter = QuestionInfoEdit_RV_adapter(myVM)
            }
        }
    }
}