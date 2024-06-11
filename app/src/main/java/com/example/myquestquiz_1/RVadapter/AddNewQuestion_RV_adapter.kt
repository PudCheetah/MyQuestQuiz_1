package com.example.myquestquiz_1.RVadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myquestquiz_1.AddQuestion.AddNewQuestionViewModel
import com.example.myquestquiz_1.Manager.KeyboardManager
import com.example.myquestquiz_1.R
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionRvItemBinding

class AddNewQuestion_RV_adapter(var myVM: AddNewQuestionViewModel) :
    RecyclerView.Adapter<AddNewQuestion_RV_adapter.AddNewQuestion_RV_holder>() {
    var keyboardManager = KeyboardManager()

    inner class AddNewQuestion_RV_holder(itemView: ActivityAddNewQuestionRvItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var TV_2_OptionNum_Num = itemView.TV2OptionNumNum
        var ET_1_ForOptionTyping = itemView.ET1ForOptionTyping
        var btn_1_ForTypingConfirm = itemView.btn1ForTypingConfirm
        var CSL_2_Top_3 = itemView.CSL2Top3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddNewQuestion_RV_holder {
        return AddNewQuestion_RV_holder(
            ActivityAddNewQuestionRvItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return myVM.spinnerChoice.value!!
    }

    override fun onBindViewHolder(holder: AddNewQuestion_RV_holder, position: Int) {
        var actionIntegrated = ActionIntegrated_AddNewQuestion(myVM, holder, position)
        //強制RV的大小
        holder.itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        with(holder) {
            //選項編號
            TV_2_OptionNum_Num.text = (position + 1).toString()
            //EditText失去焦點時的行為
            ET_1_ForOptionTyping.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (ET_1_ForOptionTyping.text.isNullOrEmpty() != true) {
                        with(actionIntegrated){
                            putStringToVM(v.context)
                            updateUI(v.context)
                            myToast(v.context)
                        }
                    }
                }
            }
            //確認按鈕的行為
            btn_1_ForTypingConfirm.setOnClickListener {
                keyboardManager.hideKeyBoard(it)
                if (ET_1_ForOptionTyping.text.isNullOrEmpty()) {
                    ET_1_ForOptionTyping.hint = "請輸入選項"
                    CSL_2_Top_3.setBackgroundColor(
                        ContextCompat.getColor(
                            it.context,
                            R.color.background_2
                        )
                    )
                }
                if (ET_1_ForOptionTyping.hint != "請輸入選項") {
                    with(actionIntegrated){
                        putStringToVM(it.context)
                        updateUI(it.context)
                        myToast(it.context)
                    }
                } else {
                    if (!ET_1_ForOptionTyping.text.isNullOrEmpty()) {
                        with(actionIntegrated){
                            putStringToVM(it.context)
                            updateUI(it.context)
                            myToast(it.context)
                        }
                    }
                    btn_1_ForTypingConfirm.requestFocus()
                }
                ET_1_ForOptionTyping.clearFocus()
            }
        }
    }

    class ActionIntegrated_AddNewQuestion(var myVM: AddNewQuestionViewModel, var holder: AddNewQuestion_RV_holder, var position: Int) {
        fun putStringToVM(context: Context) {
            with(holder) {
                ET_1_ForOptionTyping.hint = ET_1_ForOptionTyping.text
                myVM.putStringToVM(ET_1_ForOptionTyping.hint.toString(), position)
            }
        }

        fun updateUI(context: Context) {
            with(holder) {
                ET_1_ForOptionTyping.setText("")
                btn_1_ForTypingConfirm.setText("修改")
                CSL_2_Top_3.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.background_1
                    )
                )
            }
        }

        fun myToast(context: Context) {
            Toast.makeText(context, "選項${position + 1}，輸入完成", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
