package com.example.myquestquiz_1.QuestionInfoActivity

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class QuestionInfo_FunctionIntegrate(var context: Context, var myVM: QuestionInfoViewModel) {
    //將myVM中的資料存到DB
    fun stroeToDB() {
        CoroutineScope(Dispatchers.Main).launch {
            joinAll(myVM.updateQuestionNowToDB())
            Toast.makeText(context, "儲存", Toast.LENGTH_SHORT).show()
        }
    }


}