package com.example.myquestquiz_1.QuestPageActivity

import QuestPageViewModel
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.myquestquiz_1.Manager.IntentManager

class QuestPage_AlertDialogSet(var context: Context, var myVM: QuestPageViewModel, var intentManager: IntentManager) {

    fun alertDialogSet(){
        AlertDialog.Builder(context)
            .setTitle("確認視窗")
            .setMessage("即將跳轉到結果頁面  \n總題數: ${myVM.numExpect_intent.value}\n答對題數: ${myVM.scoreCounter.value!!.toInt()} \n答錯或跳過題數: ${(myVM.numExpect_intent.value!! - myVM.scoreCounter.value!!).toInt()} \n答題率: ${String.format("%.2f", (myVM.scoreCounter.value!!.toFloat() / myVM.numExpect_intent.value!!.toFloat()) * 100)}%")
            .setPositiveButton("跳轉", DialogInterface.OnClickListener { dialog, which ->
                context.startActivity(intentManager.getIntent("ToSetting"))
            }).show()
    }
}