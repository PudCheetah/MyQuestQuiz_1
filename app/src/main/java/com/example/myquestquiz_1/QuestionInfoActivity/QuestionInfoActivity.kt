package com.example.myquestquiz_1.QuestionInfoActivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myquestquiz_1.Manager.KeyboardManager
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class QuestionInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewQuestionBinding
    private lateinit var myVM: QuestionInfoViewModel
    private lateinit var functionIntegrate: QuestionInfo_FunctionIntegrate
    private lateinit var rvAdapterset: QuestionInfo_RV_AdapterSet
    private lateinit var spinnerSet: QuestionInfo_SpinnerSet
    private lateinit var modeChanger: QuestionInfo_ModeChanger
    private lateinit var btnSet: QuestionInfo_btnSet
    private var keyboardManager = KeyboardManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewQuestionBinding.inflate(layoutInflater)
        var questionIDFromIntent = intent.getLongExtra("questionID", -1)
        myVM = ViewModelProvider(
            this@QuestionInfoActivity,
            QuestionInfoViewModelFactory(application, questionIDFromIntent)
        ).get(QuestionInfoViewModel::class.java)

        functionIntegrate = QuestionInfo_FunctionIntegrate(this, myVM)
        rvAdapterset = QuestionInfo_RV_AdapterSet(this, binding, myVM)
        spinnerSet = QuestionInfo_SpinnerSet(this, binding, myVM)
        modeChanger = QuestionInfo_ModeChanger(binding, myVM, rvAdapterset)
        btnSet = QuestionInfo_btnSet(binding, myVM, modeChanger, functionIntegrate, keyboardManager)

        CoroutineScope(Dispatchers.Main).launch {
            joinAll(myVM.getVM_initJob())
            myVM.bankName.value = intent.getStringExtra("bankName")
            modeChanger.changecAtivity_toInfoMode()
            with(binding){
                TV4BankBelong.text = ("所屬題庫ID: " + "${myVM.questionqBelong.value}")
                TV5BankName.text = ("題庫名稱: " + "${myVM.bankName.value}")
                setContentView(root)
                spinnerSet.spinnerAdapterSet_ForNumOfOption()
                spinnerSet.spinnerAdapterSet_ForCorrectAns()
                spinner1ForNumOfOption.setSelection(myVM.numOfOptionInQuestionNow.value!!)
                spinner2ForCorrectAns.setSelection(myVM.questionNow.value!!.correctAns!!)
            }

            myVM.numOfOptionInQuestionNow.observe(this@QuestionInfoActivity) {
                spinnerSet.spinnerAdapterSet_ForCorrectAns()
                binding.spinner2ForCorrectAns.setSelection(myVM.questionNow.value!!.correctAns!!)
                binding.RV1ForOption?.adapter?.notifyDataSetChanged()
            }
            myVM.correctAns.observe(this@QuestionInfoActivity) {
                binding.RV1ForOption?.adapter?.notifyDataSetChanged()
            }
            btnSet.btn2Store_set()
            btnSet.btn1QuestionInputConfirm_set()
            spinnerSet.spinnerAdapterSet_ForNumOfOption_selectListener()
            spinnerSet.spinnerAdapterSet_ForCorrectAns_selectListener()
        }
    }
}