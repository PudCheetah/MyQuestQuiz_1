package com.example.myquestquiz_1.AddQuestionActivity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquestquiz_1.Manager.KeyboardManager
import com.example.myquestquiz_1.Manager.QuestionsOptionManager
import com.example.myquestquiz_1.R
import com.example.myquestquiz_1.RVadapter.AddNewQuestion_RV_adapter
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class AddNewQuestion : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewQuestionBinding
    private lateinit var myVM: AddNewQuestionViewModel
    private lateinit var modeChanger: AddQuestion_ModeChanger
    private lateinit var spinnerSet: AddQuestion_SpinnerSet
    private lateinit var btnSet: AddQuestion_btnSet
    private lateinit var rvAdapterset: AddNewQuestion_RV_AdapterSet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewQuestionBinding.inflate(layoutInflater)
        myVM = ViewModelProvider(this@AddNewQuestion).get(AddNewQuestionViewModel::class.java)
        modeChanger = AddQuestion_ModeChanger(binding)
        spinnerSet = AddQuestion_SpinnerSet(this, binding, myVM)
        btnSet = AddQuestion_btnSet(this, binding, myVM, application)
        rvAdapterset = AddNewQuestion_RV_AdapterSet(this , binding, myVM)

        CoroutineScope(Dispatchers.Main).launch {
            modeChanger.changecAtivity_toAddNewQuestionMode()
            joinAll(myVM.getVMinitJob())
            myVM.selectedBank.value = intent.getLongExtra("SelectedBank", -1)
            myVM.bankNmae.value = intent.getStringExtra("BankName")
            binding.TV4BankBelong.text = ("題庫ID: " + myVM.selectedBank.value.toString())
            binding.TV5BankName.text = ("題庫名稱: " + myVM.bankNmae.value)
            spinnerSet.spinnerAdapterSet_ForNumOfOption()
            myVM.spinnerChoice.observe(this@AddNewQuestion){
                spinnerSet.spinnerAdapterSet_ForCorrectAns()
                rvAdapterset.rvAdapterSet()
                binding.root.invalidate()
            }
            setContentView(binding.root)
        }
        btnSet.btn1QuestionInputConfirm_set()
        btnSet.btn2Store_set()
    }
}