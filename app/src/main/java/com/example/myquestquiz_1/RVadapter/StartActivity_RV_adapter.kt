package com.example.myquestquiz_1.RVadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.StartActivity.StartActivityViewModel
import com.example.myquestquiz_1.databinding.ActivityStartRvItemBinding

class StartActivity_RV_adapter(var context: Context, var myVM: StartActivityViewModel) :
    RecyclerView.Adapter<StartActivity_RV_adapter.StartActivity_RV_holder>() {
        var intentManager = IntentManager(context)
    inner class StartActivity_RV_holder(itemView: ActivityStartRvItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var TV_1_QuestionBank = itemView.TV1QuestionBank
        var btn_1_Choice = itemView.btn1Choice
        var btn_2_Delete = itemView.btn2Delete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartActivity_RV_holder {
        return StartActivity_RV_holder(ActivityStartRvItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return myVM.lisOfBank.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: StartActivity_RV_holder, position: Int) {
        var myBankNow = myVM.lisOfBank.value?.get(position)
        holder.itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        with(holder) {
            TV_1_QuestionBank.text = myBankNow?.questionBankName
            btn_1_Choice.setOnClickListener {
                intentManager.putExtra("ToSetting", "SelectedBank", myBankNow?.questionBankID)
                intentManager.putExtra("ToSetting", "BankName", myBankNow?.questionBankName)
                startActivity(context, intentManager.getIntent("ToSetting")!!, null)
            }
            btn_2_Delete.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("確認")
                    .setMessage("你確定要刪除此資料庫嗎?")
                    .setPositiveButton("確定", { dialog, which ->
                        myVM.deleteQuestionBankById(myBankNow?.questionBankID ?: 0)
                        Toast.makeText(
                            context,
                            "資料庫 ${myBankNow?.questionBankName} 已刪除",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                    .setNegativeButton("取消", { dislog, which -> })
                    .show()
            }
        }
    }
}