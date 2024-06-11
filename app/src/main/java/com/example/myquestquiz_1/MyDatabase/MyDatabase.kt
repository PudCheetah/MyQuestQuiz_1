package com.example.myquestquiz_1.MyDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MyQuestionBank::class, Question::class), version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun myQuestionDao(): MyQuestionDao

    companion object{
        private var instance: MyDatabase ?= null

        @Synchronized
        fun getInstance(context: Context ?= null): MyDatabase?{
            if(instance != null){
                return instance
            }else{
                instance = Room.databaseBuilder(context!!, MyDatabase::class.java, "MyDatabase").build()
                return instance
            }
        }
    }
}