package com.example.myquestquiz_1.Manager

import android.content.Context
import android.content.Intent
import com.example.myquestquiz_1.QuestPageActivity
import com.example.myquestquiz_1.AddQuestionActivity.AddNewQuestion
import com.example.myquestquiz_1.QuestionInfoActivity.QuestionInfoActivity
import com.example.myquestquiz_1.SettingActivity.SettingActivity

class IntentManager(context: Context) {
    private val myIntentToQuestPage = Intent(context, QuestPageActivity::class.java)
    private val myIntentToQuestionInfo = Intent(context, QuestionInfoActivity::class.java)
    private val myIntentToAddQuestion = Intent(context, AddNewQuestion::class.java)
    private val myIntentToSetting = Intent(context, SettingActivity::class.java)

    fun putExtra(toWhere: String, name: String, value: Any?) {
        val intent = getIntent(toWhere)
        when (value) {
            is Int -> {
                intent!!.putExtra(name, value)
            }

            is Boolean -> {
                intent!!.putExtra(name, value)
            }

            is Long? -> {
                intent!!.putExtra(name, value)
            }

            is Long -> {
                intent!!.putExtra(name, value)
            }

            is String -> {
                intent!!.putExtra(name, value)
            }

            else -> {
                println("------------------------------------")
                println("intent fail!!!!!!!!!")
                println("------------------------------------")
            }
        }
    }

    fun getIntent(toWhere: String): Intent? {
        var myIntent = myIntentToQuestPage
        when (toWhere) {
            "ToQuestPage" -> {
                myIntent = myIntentToQuestPage
            }

            "ToQuestionInfo" -> {
                myIntent = myIntentToQuestionInfo
            }

            "ToAddQuestion" -> {
                myIntent = myIntentToAddQuestion
            }

            "ToSetting" -> {
                myIntent = myIntentToSetting
            }

            else -> {
                println("------------------------------------")
                println("myIntent = null")
                println("------------------------------------")
            }
        }
        return myIntent
    }
}