package com.example.myquestquiz_1.Manager

import androidx.lifecycle.ViewModel
import com.example.myquestquiz_1.MyDatabase.Question

class QuestionsOptionManager() {

    //如果前面的ansN是null但後面的ansN不是Null的時候，會將後面的ansN往前移，例如:ans3 = null，但是ans4不為null時會將ans4前移為ans3
    fun optionSortByNonNull(question: Question): Question{
        var optionList = arrayOfNulls<String>(10)
        var newQuestion = Question(question.QuestionqBelong, question.questionID, question.questionTitle, null, null, null, null, null, null, null, null, null, null, question.correctAns)
        optionList[0] = question.ans1
        optionList[1] = question.ans2
        optionList[2] = question.ans3
        optionList[3] = question.ans4
        optionList[4] = question.ans5
        optionList[5] = question.ans6
        optionList[6] = question.ans7
        optionList[7] = question.ans8
        optionList[8] = question.ans9
        optionList[9] = question.ans10
        for(i in 0 until  optionList.size){
            for(j in 0 until (optionList.size - 1 - i)){
                if(optionList[j].isNullOrEmpty() && (optionList[j + 1].isNullOrEmpty() == false)){
                    val temp = optionList.get(j)
                    optionList[j] = optionList[j + 1]
                    optionList[j + 1] = temp
                }
            }
        }
        newQuestion.ans1 = optionList[0]
        newQuestion.ans2 = optionList[1]
        newQuestion.ans3 = optionList[2]
        newQuestion.ans4 = optionList[3]
        newQuestion.ans5 = optionList[4]
        newQuestion.ans6 = optionList[5]
        newQuestion.ans7 = optionList[6]
        newQuestion.ans8 = optionList[7]
        newQuestion.ans9 = optionList[8]
        newQuestion.ans10 = optionList[9]
        return newQuestion
    }

    //填入選項數目後生成一個隨機排列的整數的串列
    fun optionShuffled(ansTotal: Int): List<Int>{
        var shuffledList =(0..ansTotal).toList().shuffled()
        return shuffledList
    }

    //回傳有多少個答案不是null，須先用 optionSortByNonNull() 進行排列
    fun getNotNullAnsTotal(question: Question): Int{
        return when{
            question.ans10 != null -> {return 10}
            question.ans9 != null -> {return 9}
            question.ans8 != null -> {return 8}
            question.ans7 != null -> {return 7}
            question.ans6 != null -> {return 6}
            question.ans5 != null -> {return 5}
            question.ans4 != null -> {return 4}
            question.ans3 != null -> {return 3}
            question.ans2 != null -> {return 2}
            question.ans1 != null -> {return 1}
            else -> {return 0}
        }
    }

    //根據position將數據導向對應的ans
    fun optionProvider(question: Question, position: Int): String?{
        when(position){
            0 -> return question.ans1
            1 -> return question.ans2
            2 -> return question.ans3
            3 -> return question.ans4
            4 -> return question.ans5
            5 -> return question.ans6
            6 -> return question.ans7
            7 -> return question.ans8
            8 -> return question.ans9
            9 -> return question.ans10
            else -> {return null}
        }
    }

}