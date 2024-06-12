package com.example.myquestquiz_1.SettingActivity

import android.content.Context
import com.example.myquestquiz_1.Manager.IntentManager

class Setting_switchSet(var TM: Setting_toastManager, var intentManager: IntentManager) {
    //題目隨機排列開關行為
    fun shuffledTitleSwitchAction(isCheck: Boolean, context: Context) {
        if (isCheck == true) {
            intentManager.putExtra("ToQuestPage", "ShuffledTitleSwitch", true)
            TM.toastManager("shuffledTitleSwitchAction_on", context)
        } else {
            intentManager.putExtra("ToQuestPage", "ShuffledTitleSwitch", false)
            TM.toastManager("shuffledTitleSwitchAction_off", context)
        }
    }

    //選項隨機排列開關行為
    fun shuffledQuestionSwitchAction(isCheck: Boolean, context: Context) {
        if (isCheck == true) {
            intentManager.putExtra("ToQuestPage", "ShuffledQuestion", true)
            TM.toastManager("shuffledQuestionSwitchAction_on", context)
        } else {
            intentManager.putExtra("ToQuestPage", "ShuffledQuestion", false)
            TM.toastManager("shuffledQuestionSwitchAction_off", context)
        }
    }
}