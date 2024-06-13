package com.example.myquestquiz_1.Manager

import android.util.Log
import kotlin.random.Random

class ShuffleAndFillManager {
    //需求三個參數:串列、串列元素的需求數、是否隨機排列的布林值
    //返回一個符合需求的新串列
    fun shufflerAndFill_indext(
        originalList: List<Int>,
        totalInNeed: Int,
        shuffledTitleSwitchAction: Boolean
    ): List<Int> {
        var shuffledOriginal_indexList = (0..(originalList.size - 1)).toList().shuffled()
        var modifiedList = ArrayList(originalList)
        when (totalInNeed > modifiedList.size) {
            true -> {
                while (modifiedList.size <= totalInNeed) {
                    var random = Random.nextInt(shuffledOriginal_indexList.size - 1)
                    modifiedList.add(shuffledOriginal_indexList.get(random))
                }
                if (shuffledTitleSwitchAction == true) {
                    Log.d("myTag", "shufflerAndFill_indext: true,true")
                    return modifiedList.shuffled()
                } else {
                    Log.d("myTag", "shufflerAndFill_indext: true, else")
                    return modifiedList
                }
            }

            false -> {
                if (shuffledTitleSwitchAction == true) {
                    while (modifiedList.size > totalInNeed) {
                        var random = Random.nextInt(modifiedList.size)
                        modifiedList.removeAt(random)
                    }
                    Log.d("myTag", "shufflerAndFill_indext: false, true")
                    return modifiedList.shuffled()
                } else {
                    while (totalInNeed < modifiedList.size) {
                        modifiedList.removeLast()
                    }
                    Log.d("myTag", "shufflerAndFill_indext: false, false")
                    return modifiedList
                }
            }
        }
    }
}