package com.example.myquestquiz_1.Manager

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardManager {
    fun hideKeyBoard(view: View){
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}