package com.example.myquestquiz_1.MyDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface MyQuestionDao {
    @Upsert
    fun upsertQuestionBank(myQuestionBank: MyQuestionBank)

    @Upsert
    fun upsertQuestion(question: Question)

    @Update
    fun updateQuestion(question: Question)


    @Transaction
    @Query("SELECT * FROM MyQuestionBank WHERE questionBankID = :questionBankId")
    fun getAllQuestionInQuestionBank(questionBankId: Long): List<QuestionAndQuestionBank>

    @Query("select Count(*) From MyQuestionBank WHERE questionBankID = :questionBankId")
    fun getCountQuestionInQuestionBank(questionBankId: Long): Long
    @Query("select Count(*) From Question WHERE QuestionqBelong = :questionqBelong")
    fun getCountOfQuestionByQuestionqBelong(questionqBelong: Long): Long

    @Query("select * From Question where QuestionqBelong = :questionqBelong")
    fun getAllQuestionByQuestionqBelong(questionqBelong: Long): List<Question>

    @Query("select * from MyQuestionBank order by questionBankID")
    fun getAllQuestionBank(): List<MyQuestionBank>

    @Query("delete from MyQuestionBank where questionBankID = :myquestionBankId")
    fun deleteQuestionBankById(myquestionBankId: Long)

    @Query("select * from Question where QuestionID = :myQuestionID")
    fun getQuestionByID(myQuestionID: Long): Question

    @Query("delete from Question where QuestionID = :myQuestionID")
    fun deleteQuestionByID(myQuestionID: Long)
}
