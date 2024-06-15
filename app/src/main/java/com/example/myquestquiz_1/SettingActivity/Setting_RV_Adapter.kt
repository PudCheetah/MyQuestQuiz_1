package com.example.myquestquiz_1.SettingActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.Manager.QuestionsTitleManager
import com.example.myquestquiz_1.databinding.ActivitySettingRvItemBinding

class Setting_RV_Adapter(context: Context, var myVM: SettingActivityViewModel): RecyclerView.Adapter<Setting_RV_Adapter.SettingActivity_RV_holder>() {
    var questionList = myVM.questionsListNow.value
    var questionsTitleManager = QuestionsTitleManager()
    var intentManager = IntentManager(context)
    inner class SettingActivity_RV_holder(itemView: ActivitySettingRvItemBinding): RecyclerView.ViewHolder(itemView.root){
        var TV_1_QuestionTitle = itemView.TV1QuestionTitle
        var TV_2_QuestionID = itemView.TV2QuestionID
        var btn_1_JumpToDetail = itemView.btn1JumpToDetail
        var btn_2_deleteQuestion = itemView.btn2DeleteQuestion
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingActivity_RV_holder {
        return SettingActivity_RV_holder(ActivitySettingRvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return questionList?.size ?: 0
    }

    override fun onBindViewHolder(holder: SettingActivity_RV_holder, position: Int) {
        holder.itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        with(holder){
            TV_1_QuestionTitle.text = questionList?.let {
                questionsTitleManager.questionTitleOverView(
                    it.get(position))
            }
            TV_2_QuestionID.text = "ID: ${questionList?.get(position)?.questionID.toString()}"
            btn_1_JumpToDetail.setOnClickListener {
                //傳遞所選擇的Question的QuestionID
                intentManager.putExtra("ToQuestionInfo", "questionID", questionList?.get(position)?.questionID)
                intentManager.putExtra("ToQuestionInfo", "bankName", myVM.bankName.value)
                ContextCompat.startActivity(it.context, intentManager.getIntent("ToQuestionInfo")!!, null)
            }
            btn_2_deleteQuestion.setOnClickListener {
                myVM.deleteQuestionByID(questionList?.get(position)?.questionID!!, myVM.selectedBank.value!!)
            }
        }
    }
}