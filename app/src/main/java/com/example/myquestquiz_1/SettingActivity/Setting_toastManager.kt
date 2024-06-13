package com.example.myquestquiz_1.SettingActivity

import android.content.Context
import android.widget.Toast

class Setting_toastManager() {
    //Toast訊息整合
    fun toastManager(string: String, context: Context){
        var myString = ""
        when(string){
            "shuffledTitleSwitchAction_on" -> {"亂數題目啟動"}
            "shuffledTitleSwitchAction_ off" -> {"亂數題目關閉"}
            "shuffledOptionSwitchAction_on" -> {"亂數選項啟動"}
            "shuffledOptionSwitchAction_off" -> {"亂數選項關閉"}
            "btn2NumOfQuestionConfirm" -> {"題目數量已確認"}
        }
        Toast.makeText(context, myString, Toast.LENGTH_SHORT).show()
    }
}