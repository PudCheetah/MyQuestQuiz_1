package com.example.myquestquiz_1.Manager

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.myquestquiz_1.QuestPageActivity.QuestPageActivity
import com.example.myquestquiz_1.AddQuestionActivity.AddNewQuestion
import com.example.myquestquiz_1.QuestionInfoActivity.QuestionInfoActivity
import com.example.myquestquiz_1.ResultPage.ResultPageActivity
import com.example.myquestquiz_1.SettingActivity.SettingActivity

class IntentManager(context: Context) {
    private val myIntentToQuestPage = Intent(context, QuestPageActivity::class.java)
    private val myIntentToQuestionInfo = Intent(context, QuestionInfoActivity::class.java)
    private val myIntentToAddQuestion = Intent(context, AddNewQuestion::class.java)
    private val myIntentToSetting = Intent(context, SettingActivity::class.java)
    private val myIntentToResultPage = Intent(context, ResultPageActivity::class.java)


    //將value放到指定的地點
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

            is Double -> {
                intent!!.putExtra(name, value)
            }

            is Double? -> {
                intent!!.putExtra(name, value)
            }

            else -> {
                println("------------------------------------")
                println("intent fail!!!!!!!!!")
                println("------------------------------------")
            }
        }
    }

    //獲得指定的intent
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

            "ToResult" -> {
                myIntent = myIntentToResultPage
            }

            else -> {
            }
        }
        return myIntent
    }
}