package com.example.myquestquiz_1.AddQuestion

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewQuestionBinding.inflate(layoutInflater)
        changecAtivity_toAddNewQuestionMode()
        myVM = ViewModelProvider(this@AddNewQuestion).get(AddNewQuestionViewModel::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            joinAll(myVM.getVMinitJob())
            myVM.selectedBank.value = intent.getLongExtra("SelectedBank", -1)
            myVM.bankNmae.value = intent.getStringExtra("BankName")
            binding.TV4BankBelong.text = ("題庫ID: " + myVM.selectedBank.value.toString())
            binding.TV5BankName.text = ("題庫名稱: " + myVM.bankNmae.value)
            spinnerAdapterSet_ForNumOfOption()
            myVM.spinnerChoice.observe(this@AddNewQuestion){
                spinnerAdapterSet_ForCorrectAns()
                rvAdapterSet()
                binding.root.invalidate()
            }
            setContentView(binding.root)
        }
        binding.btn1QuestionInputConfirm.setOnClickListener {
            binding.RV1ForOption.clearFocus()
            questionTitleConfirmBtnAction()
        }
        binding.btn2Store.setOnClickListener {
            storeBtnAction()
        }
    }


    //Spinner設置1
    fun spinnerAdapterSet_ForNumOfOption(){
        var myResource = resources.getStringArray(R.array.activityNewQuestionSpinner)
        var mySpinnerAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, myResource)
        mySpinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.spinner1ForNumOfOption.adapter = mySpinnerAdapter
        binding.spinner1ForNumOfOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                myVM.spinnerChoice.value = position
                myVM.clearQuestionInfo()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    //Spinner設置2
    fun spinnerAdapterSet_ForCorrectAns(){
        var myResource = (0..myVM.spinnerChoice.value!!).toList()
        var mySpinnerAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, myResource)
        binding.spinner2ForCorrectAns.adapter = mySpinnerAdapter
        binding.spinner2ForCorrectAns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                myVM.correctAns.value = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }



    //Adapter設置
    fun rvAdapterSet(){
        with(binding.RV1ForOption){
            layoutManager = LinearLayoutManager(this@AddNewQuestion)
            setHasFixedSize(true)
            adapter = AddNewQuestion_RV_adapter(myVM)
        }
    }

    //題目按鈕行為
    fun questionTitleConfirmBtnAction(){
        var keyboardManager = KeyboardManager()
        keyboardManager.hideKeyBoard(binding.root)
        binding.ET1ForQuestionTyping.clearFocus()
        binding.root.requestFocus()
        if (binding.ET1ForQuestionTyping.text.isEmpty()){
            Toast.makeText(this, "錯誤，題目不可為空", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "題目輸入完成", Toast.LENGTH_SHORT).show()
            myVM.questionTitleInput.value = binding.ET1ForQuestionTyping.text.toString()
        }
    }

    //儲存按鈕行為
    fun storeBtnAction(){
        var questionsOptionManager = QuestionsOptionManager()
        if (binding.ET1ForQuestionTyping.text.isEmpty()){
            Toast.makeText(this, "錯誤，題目不可為空", Toast.LENGTH_SHORT).show()
        }else{
            CoroutineScope(Dispatchers.Main).launch {
                myVM.questionTitleInput.value = binding.ET1ForQuestionTyping.text.toString()
                myVM.packageToQuestion()
                var newQuestion = questionsOptionManager.optionSortByNonNull(myVM.myQuestion.value!!)
                myVM.updateMyQuestionByQuestion(newQuestion)
                joinAll(myVM.storeQuestionToDatabase(application, myVM.myQuestion.value!!))
                Toast.makeText(application, "儲存完成，可返回上一頁或繼續新增", Toast.LENGTH_SHORT).show()
                binding.ET1ForQuestionTyping.setText("")
                myVM.clearQuestionInfo()
                binding.spinner1ForNumOfOption.setSelection(0)
                binding.spinner2ForCorrectAns.setSelection(0)
            }
        }
    }

    //將activity轉換成此Activity的模式
    fun changecAtivity_toAddNewQuestionMode(){
        binding.TV7QuestionTitle.isInvisible = true
        binding.TV6QuestionID.isInvisible = true
    }
}