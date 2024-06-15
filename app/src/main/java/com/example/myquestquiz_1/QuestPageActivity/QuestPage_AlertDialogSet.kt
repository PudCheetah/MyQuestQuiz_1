package com.example.myquestquiz_1.QuestPageActivity

import QuestPageViewModel
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.R

class QuestPage_AlertDialogSet(var context: Context, var myVM: QuestPageViewModel, var intentManager: IntentManager) {

    fun alertDialogSet(){
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.AlertDialog_confirmWindows))
            .setMessage(
                context.getString(
                    R.string.alertDialogMessage_questResult,
                    myVM.numExpect_intent.value,
                    myVM.scoreCounter.value!!.toInt(),
                    (myVM.numExpect_intent.value!! - myVM.scoreCounter.value!!).toInt(),
                    String.format("%.2f", (myVM.scoreCounter.value!!.toFloat() / myVM.numExpect_intent.value!!.toFloat()) * 100)
                ))
            .setPositiveButton("跳轉", DialogInterface.OnClickListener { dialog, which ->
                context.startActivity(intentManager.getIntent("ToStart"))
            }).show()
    }
}