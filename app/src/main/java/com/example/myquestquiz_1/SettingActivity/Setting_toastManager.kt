package com.example.myquestquiz_1.SettingActivity

import android.content.Context
import android.widget.Toast
import com.example.myquestquiz_1.R

class Setting_toastManager() {
    //Toast訊息整合
    fun toastManager(string: String, context: Context){
        var myString = ""
        when(string){
            "shuffledTitleSwitchAction_on" -> {
                context.getString(R.string.shuffledTitleON)}
            "shuffledTitleSwitchAction_ off" -> {
                context.getString(R.string.shuffledTitleOFF)}
            "shuffledOptionSwitchAction_on" -> {
                context.getString(R.string.shuffledOptionOn)}
            "shuffledOptionSwitchAction_off" -> {
                context.getString(R.string.shuffledOptionOFF)}
            "btn2NumOfQuestionConfirm" -> {
                context.getString(R.string.titleNumConfirmed)}
        }
        Toast.makeText(context, myString, Toast.LENGTH_SHORT).show()
    }
}