package com.example.myquestquiz_1.Manager

import com.example.myquestquiz_1.MyDatabase.Question

class QuestionsTitleManager {
    //將題目縮減為前9個字 + "..."
    fun questionTitleOverView(question: Question): String {
        var titleSplitList = question.questionTitle!!.split("")
        var titleOverView = ""
        when(titleSplitList.size){
            in 0..10 -> { titleOverView = question.questionTitle!!}
            else -> { titleOverView = titleSplitList[0] + titleSplitList[1] + titleSplitList[2] + titleSplitList[3] + titleSplitList[4] + titleSplitList[5] + titleSplitList[6]+ titleSplitList[7]+ titleSplitList[8]+ titleSplitList[9] + "..."}
        }
        return titleOverView
    }

    fun getQuestionNowTitle(list: List<Question>, index: Int): String{
        return list.get(index).questionTitle!!
    }

    fun shuffledQuestionTitle(){}
}