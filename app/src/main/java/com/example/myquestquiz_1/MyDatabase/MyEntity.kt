package com.example.myquestquiz_1.MyDatabase
import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity
data class MyQuestionBank(
    @PrimaryKey(autoGenerate = true)
    val questionBankID: Long = 0,
    @SerializedName("QuestionBankNum")
    val questionBankName: String,
)

@Entity
data class Question(
    val QuestionqBelong: Long ?= 0,
    @PrimaryKey(autoGenerate = true)
    val questionID: Long ?= 1,
    @SerializedName("questionTitle")
    var questionTitle: String ?= null,
    @SerializedName("Ans1")
    var ans1: String ?= null,
    @SerializedName("Ans2")
    var ans2: String ?= null,
    @SerializedName("Ans3")
    var ans3: String ?= null,
    @SerializedName("Ans4")
    var ans4: String ?= null,
    @SerializedName("Ans5")
    var ans5: String ?= null,
    @SerializedName("Ans6")
    var ans6: String ?= null,
    @SerializedName("Ans7")
    var ans7: String ?= null,
    @SerializedName("Ans8")
    var ans8: String ?= null,
    @SerializedName("Ans9")
    var ans9: String ?= null,
    @SerializedName("Ans10")
    var ans10: String ?= null,
    @SerializedName("Correct Ans")
    var correctAns: Int ?= 0
)

data class QuestionAndQuestionBank(
    @Embedded
    val questionBank: MyQuestionBank,
    @Relation(parentColumn = "questionBankID", entityColumn = "QuestionqBelong")
    val questions: List<Question>
)