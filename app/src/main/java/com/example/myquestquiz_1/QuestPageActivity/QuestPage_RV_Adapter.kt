package com.example.myquestquiz_1.QuestPageActivity

import QuestPageViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.Manager.QuestionsOptionManager
import com.example.myquestquiz_1.MyDatabase.Question
import com.example.myquestquiz_1.R
import com.example.myquestquiz_1.databinding.ActivityQuestPageRvItemBinding

class QuestPage_RV_Adapter(var myVM: QuestPageViewModel, var question: Question, var intentManager: IntentManager): RecyclerView.Adapter<QuestPage_RV_Adapter.QuestPageActivity_RV_holder>() {
    private var questionsOptionManager = QuestionsOptionManager()
    private var numOfOption: Int = questionsOptionManager.getNotNullAnsTotal(question)
    private var shuffOptionOrder_list = questionsOptionManager.optionShuffled(numOfOption - 1)

    inner class QuestPageActivity_RV_holder(itemView: ActivityQuestPageRvItemBinding): RecyclerView.ViewHolder(itemView.root){
        var TV_1_Option = itemView.TV1Option
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestPageActivity_RV_holder {
        return QuestPageActivity_RV_holder(ActivityQuestPageRvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return numOfOption
    }

    override fun onBindViewHolder(holder: QuestPageActivity_RV_holder, position: Int) {
        with(holder){
            itemView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            TV_1_Option.text = myVM.optionProvider(question, shuffOptionOrder_list.get(position)).toString()
            TV_1_Option.setOnClickListener {
                if(myVM.isHadAns.value == false){
                    if((shuffOptionOrder_list.get(position)+ 1) == question.correctAns!!){
                        myVM.scoreCounter.value = myVM.scoreCounter.value!! + 1
                        Toast.makeText(it.context,
                            it.context.getString(R.string.AnsCorrect), Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(it.context,
                            it.context.getString(R.string.AnsWrong), Toast.LENGTH_SHORT).show()
                    }
                    myVM.isHadAns.value = true
            }else{
                Toast.makeText(it.context,
                    it.context.getString(R.string.haveAnswered), Toast.LENGTH_SHORT).show()
            }
//                if (myVM.numExpect_intent.value!! >= (myVM.progressControler.value!! + 1)){
//                    if((shuffOptionOrder_list.get(position)+ 1) == question.correctAns!!){
//                        myVM.scoreCounter.value = myVM.scoreCounter.value!! + 1
//                        Toast.makeText(it.context, "答案正確", Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(it.context, "答案錯誤", Toast.LENGTH_SHORT).show()
//                    }
//                    if (myVM.numExpect_intent.value!! > (myVM.progressControler.value!! + 1)){
//                        myVM.progressControler.value = myVM.progressControler.value!!.plus(1)
//                    }
//                }else{
//                    Toast.makeText(it.context, "已經到底了", Toast.LENGTH_SHORT).show()
//                    intentManager.putExtra("ToResult", "scoreCounter", myVM.scoreCounter.value)
//                }
            }
        }
    }
}