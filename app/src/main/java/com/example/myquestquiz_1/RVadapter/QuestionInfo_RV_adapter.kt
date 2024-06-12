package com.example.myquestquiz_1.RVadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myquestquiz_1.QuestionInfoActivity.QuestionInfoViewModel
import com.example.myquestquiz_1.databinding.ActivityQuestionInfoRvItemBinding

class QuestionInfo_RV_adapter(var myVM: QuestionInfoViewModel): RecyclerView.Adapter<QuestionInfo_RV_adapter.QuestionInfo_RV_holder>() {

    inner class QuestionInfo_RV_holder(itemView: ActivityQuestionInfoRvItemBinding): RecyclerView.ViewHolder(itemView.root){
        var TV_1_optionText = itemView.TV1OptionText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionInfo_RV_holder {
        return QuestionInfo_RV_holder(ActivityQuestionInfoRvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return myVM.numOfOptionInQuestionNow.value!!
    }

    override fun onBindViewHolder(holder: QuestionInfo_RV_holder, position: Int) {
        holder.itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        holder.TV_1_optionText.text = myVM.getValueFromQuestionNow(position)
    }
}