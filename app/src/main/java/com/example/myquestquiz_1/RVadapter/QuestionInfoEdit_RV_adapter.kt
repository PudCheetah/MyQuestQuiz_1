package com.example.myquestquiz_1.RVadapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myquestquiz_1.Manager.KeyboardManager
import com.example.myquestquiz_1.Manager.QuestionsOptionManager
import com.example.myquestquiz_1.QuestionInfoActivity.QuestionInfoViewModel
import com.example.myquestquiz_1.databinding.ActivityQuestionInfoEditRvItemBinding

class QuestionInfoEdit_RV_adapter(var myVM: QuestionInfoViewModel) :
    RecyclerView.Adapter<QuestionInfoEdit_RV_adapter.QuestionInfoEdit_RV_Holder>() {
    var questionsOptionManager = QuestionsOptionManager()
    var keyboardManager = KeyboardManager()

    inner class QuestionInfoEdit_RV_Holder(itemView: ActivityQuestionInfoEditRvItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var ET_1_forOptionTyping = itemView.ET1ForOptionTyping
        var btn_2_delete = itemView.btn2Delete
        var btn_1_confirm = itemView.btn1Confirm
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionInfoEdit_RV_Holder {
        return QuestionInfoEdit_RV_Holder(
            ActivityQuestionInfoEditRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
//        myVM.updateQuestionNowWithValueInVM()
//        myVM.numOfOptionInQuestionNow.value = questionsOptionManager.getNotNullAnsTotal(myVM.questionNow.value!!)
        var temp = myVM.numOfOptionInQuestionNow.value
        return temp!!
    }

    override fun onBindViewHolder(holder: QuestionInfoEdit_RV_Holder, position: Int) {
        with(holder) {
            itemView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            with(holder){
                ET_1_forOptionTyping.setText(myVM.getValueFromQuestionNow(position))
                btn_2_delete.setOnClickListener {
                    var value = null
                    myVM.putValueToQuestionNow(position, value)
                    var temp = questionsOptionManager.optionSortByNonNull(myVM.questionNow.value!!)
                    myVM.questionNow.value = temp
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
                btn_1_confirm.setOnClickListener {
                    var string = ET_1_forOptionTyping.text
                    myVM.putValueToQuestionNow(position, string.toString())
                    ET_1_forOptionTyping.clearFocus()
                    keyboardManager.hideKeyBoard(it)
                }
            }

        }
    }
}