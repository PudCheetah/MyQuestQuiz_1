package com.example.myquestquiz_1.QuestionInfo

import android.os.Bundle
import android.util.Log
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
import com.example.myquestquiz_1.RVadapter.QuestionInfoEdit_RV_adapter
import com.example.myquestquiz_1.RVadapter.QuestionInfo_RV_adapter
import com.example.myquestquiz_1.databinding.ActivityAddNewQuestionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class QuestionInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewQuestionBinding
    private lateinit var myVM: QuestionInfoViewModel
    private var questionsOptionManager = QuestionsOptionManager()
    private var keyboardManager = KeyboardManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewQuestionBinding.inflate(layoutInflater)
        var questionIDFromIntent = intent.getLongExtra("questionID", -1)
        myVM = ViewModelProvider(
            this@QuestionInfoActivity,
            QuestionInfoViewModelFactory(application, questionIDFromIntent)
        ).get(QuestionInfoViewModel::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            joinAll(myVM.getVM_initJob())
            myVM.bankName.value = intent.getStringExtra("bankName")
            changecAtivity_toInfoMode()
            binding.TV4BankBelong.text = ("所屬題庫ID: " + "${myVM.questionqBelong.value}")
            binding.TV5BankName.text = ("題庫名稱: " + "${myVM.bankName.value}")
            setContentView(binding.root)
            spinnerAdapterSet_ForNumOfOption()
            spinnerAdapterSet_ForCorrectAns()
            binding.spinner1ForNumOfOption.setSelection(myVM.numOfOptionInQuestionNow.value!!)
            binding.spinner2ForCorrectAns.setSelection(myVM.questionNow.value!!.correctAns!!)

            myVM.numOfOptionInQuestionNow.observe(this@QuestionInfoActivity) {
                spinnerAdapterSet_ForCorrectAns()
                binding.spinner2ForCorrectAns.setSelection(myVM.questionNow.value!!.correctAns!!)
                Log.d("myTag", "spinner2ForCorrectAns.setSelection Activity")
                binding.RV1ForOption?.adapter?.notifyDataSetChanged()
            }
            myVM.correctAns.observe(this@QuestionInfoActivity){

                binding.RV1ForOption?.adapter?.notifyDataSetChanged()
            }

            binding.btn2Store.setOnClickListener {
                if (binding.btn2Store.text == "編輯") {
                    changeActivity_toEditMode()
                } else {
                    changecAtivity_toInfoMode()
                    stroeToDB()
                }
            }
            binding.btn1QuestionInputConfirm.setOnClickListener {
                myVM.questionNow.value!!.questionTitle =
                    binding.ET1ForQuestionTyping.text.toString()
                binding.ET1ForQuestionTyping.clearFocus()
                keyboardManager.hideKeyBoard(it)
            }
            spinnerAdapterSet_ForNumOfOption_selectListener()
            spinnerAdapterSet_ForCorrectAns_selectListener()
        }
    }

    //切換介面到info模式
    fun changecAtivity_toInfoMode() {
        with(binding) {
            TV7QuestionTitle.isInvisible = false
            ET1ForQuestionTyping.isInvisible = true
            TV2ForNumOfOptionText.isInvisible = true
            spinner1ForNumOfOption.isInvisible = true
            btn1QuestionInputConfirm.isInvisible = true
            TV3CorrectAnsText.isInvisible = true
            spinner1ForNumOfOption.isInvisible = true
            spinner2ForCorrectAns.isInvisible = true
            adapterSet("infoMode")
            btn2Store.setText("編輯")
            binding.TV6QuestionID.text = ("題目ID: " + "${myVM.questionNow.value!!.questionID}")
            binding.TV7QuestionTitle.text = ("${myVM.questionNow.value!!.questionTitle}")
            binding.root.invalidate()
        }
    }

    //切換模式到編輯模式
    fun changeActivity_toEditMode() {
        with(binding) {
            TV7QuestionTitle.isInvisible = true
            ET1ForQuestionTyping.isInvisible = false
            TV2ForNumOfOptionText.isInvisible = false
            spinner1ForNumOfOption.isInvisible = false
            btn1QuestionInputConfirm.isInvisible = false
            TV3CorrectAnsText.isInvisible = false
            spinner1ForNumOfOption.isInvisible = false
            spinner2ForCorrectAns.isInvisible = false
            binding.TV7QuestionTitle.text = ("${myVM.questionNow.value!!.questionTitle}")
            ET1ForQuestionTyping.setText(myVM.questionTitle.value.toString())
            binding.spinner1ForNumOfOption.setSelection(myVM.numOfOptionInQuestionNow.value!!)
            binding.spinner2ForCorrectAns.setSelection(myVM.questionNow.value!!.correctAns!!)
            adapterSet("editMode")
            btn2Store.setText("儲存")
            binding.root.invalidate()
        }
    }

    //跟據string切換Adapter
    fun adapterSet(string: String) {
        binding.RV1ForOption.layoutManager = LinearLayoutManager(this)
        binding.RV1ForOption.setHasFixedSize(true)
        when (string) {
            "infoMode" -> {
                binding.RV1ForOption.adapter = QuestionInfo_RV_adapter(myVM)
            }

            "editMode" -> {
                binding.RV1ForOption.adapter = QuestionInfoEdit_RV_adapter(myVM)
            }
        }
    }

    fun stroeToDB() {
        CoroutineScope(Dispatchers.Main).launch {
            joinAll(myVM.updateQuestionNowToDB())
            Toast.makeText(this@QuestionInfoActivity, "儲存", Toast.LENGTH_SHORT).show()
        }
    }

    fun spinnerAdapterSet_ForNumOfOption() {
        var myResource = resources.getStringArray(R.array.activityNewQuestionSpinner)
        var mySpinnerAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            myResource
        )
        mySpinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.spinner1ForNumOfOption.adapter = mySpinnerAdapter
    }
    fun spinnerAdapterSet_ForNumOfOption_selectListener(){
        binding.spinner1ForNumOfOption.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if(myVM.numOfOptionInQuestionNow.value != position){
                        myVM.numOfOptionInQuestionNow.value = position
                    }
                    Log.d(
                        "myTag",
                        "myVM.numOfOptionInQuestionNow.value---inSpinner ${myVM.numOfOptionInQuestionNow.value}"
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }

    fun spinnerAdapterSet_ForCorrectAns() {
        var myResource: List<Int>
        if (myVM.questionNow.value!!.correctAns!! > myVM.numOfOptionInQuestionNow.value!!) {
            myResource = (0..myVM.questionNow.value!!.correctAns!!).toList()
        } else {
            myResource = (0..myVM.numOfOptionInQuestionNow.value!!).toList()
        }
        var mySpinnerAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            myResource
        )
        binding.spinner2ForCorrectAns.adapter = mySpinnerAdapter

    }
    fun spinnerAdapterSet_ForCorrectAns_selectListener(){
        binding.spinner2ForCorrectAns.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if(myVM.questionNow.value!!.correctAns != position){
                        myVM.questionNow.value!!.correctAns = position
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }

}