package com.example.myquestquiz_1.MyDatabase
import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class MyQuestionBank(
    @PrimaryKey(autoGenerate = true)
    val questionBankID: Long = 0,
    val questionBankName: String,
)

@Entity
data class Question(
    val QuestionqBelong: Long ?= 0,
    @PrimaryKey(autoGenerate = true)
    val questionID: Long ?= 1,
    var questionTitle: String ?= null,
    var ans1: String ?= null,
    var ans2: String ?= null,
    var ans3: String ?= null,
    var ans4: String ?= null,
    var ans5: String ?= null,
    var ans6: String ?= null,
    var ans7: String ?= null,
    var ans8: String ?= null,
    var ans9: String ?= null,
    var ans10: String ?= null,
    var correctAns: Int ?= 0
)

data class QuestionAndQuestionBank(
    @Embedded
    val questionBank: MyQuestionBank,
    @Relation(parentColumn = "questionBankID", entityColumn = "QuestionqBelong")
    val questions: List<Question>
)