package com.example.myquestquiz_1.Setting

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.Manager.KeyboardManager
import com.example.myquestquiz_1.RVadapter.SettingActivity_RV_adapter
import com.example.myquestquiz_1.databinding.ActivitySettingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

//選擇完題庫後設定考試方法的頁面
class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var onCreatJob: Job
    private lateinit var myVM: SettingActivityViewModel
    private lateinit var intentManager: IntentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreatJob = CoroutineScope(Dispatchers.Main).launch {
            binding = ActivitySettingBinding.inflate(layoutInflater)
            myVM = ViewModelProvider(this@SettingActivity).get(SettingActivityViewModel::class.java)
            intentManager = IntentManager(this@SettingActivity)
            joinAll(myVM.getVMinitJob())
            setContentView(binding.root)
            binding.switch1IsShuffledTitle.setOnCheckedChangeListener { buttonView, isChecked ->
                myVM.shuffledTitleSwitch.value = isChecked
                shuffledTitleSwitchAction(isChecked)
            }
            binding.switch2IsShuffledOption.setOnCheckedChangeListener { buttonView, isChecked ->
                myVM.shuffledQuestionSwitch.value = isChecked
                shuffledQuestionSwitchAction(isChecked)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).launch {
            joinAll(onCreatJob)
            myVM.selectedBank.value = intent.getLongExtra("SelectedBank", -1)
            myVM.bankName.value = intent.getStringExtra("BankName")

            binding.TV6BankID.text = ("題庫ID: " + myVM.selectedBank.value.toString())
            binding.TV7BankName.text = ("題庫名稱: " + myVM.bankName.value)
            joinAll(myVM.updateQuestionsListNow(myVM.selectedBank.value!!))
            myVM.updateQuestionTotl(myVM.questionsListNow.value)
            myVM.questionsListNow.observe(this@SettingActivity){
                binding.TV5NumOfBankNum.text = myVM.questionsListNow.value?.size.toString()
                adapterSetting()
            }
            binding.switch1IsShuffledTitle.isChecked = myVM.shuffledTitleSwitch.value ?: true
            binding.switch2IsShuffledOption.isChecked = myVM.shuffledQuestionSwitch.value ?: true
        }
    }



    //按鈕行為整合
    fun activitySettingOnClick(view: View){
        when(view){
            binding.btn2NumOfQuestionConfirm -> {
                binding.numET1TypeNumOfQuestion.clearFocus()
                var keyboardManager = KeyboardManager()
                keyboardManager.hideKeyBoard(view)
                toastManager("btn2NumOfQuestionConfirm", this)
            }
            binding.btn1ToQuestPage -> {
                testStartBtnAction()
            }
            binding.btn3ToAddQuestionPage -> {
                addQuestioBtnAction()
            }
        }
    }

    //題目隨機排列開關行為
    fun shuffledTitleSwitchAction(isCheck: Boolean) {
        if (isCheck == true) {
            intentManager.putExtra("ToQuestPage", "ShuffledTitleSwitch", true)
            toastManager("shuffledTitleSwitchAction_on", this)
        } else {
            intentManager.putExtra("ToQuestPage", "ShuffledTitleSwitch", false)
            toastManager("shuffledTitleSwitchAction_off", this)
        }
    }

    //選項隨機排列開關行為
    fun shuffledQuestionSwitchAction(isCheck: Boolean) {
        if (isCheck == true) {
            intentManager.putExtra("ToQuestPage", "ShuffledQuestion", true)
            toastManager("shuffledQuestionSwitchAction_on", this)
        } else {
            intentManager.putExtra("ToQuestPage", "ShuffledQuestion", false)
            toastManager("shuffledQuestionSwitchAction_off", this)
        }
    }
    //RV設定
    fun adapterSetting(){
        with(binding.RV1RvForQuestions){
            layoutManager = LinearLayoutManager(this@SettingActivity)
            setHasFixedSize(true)
            adapter = SettingActivity_RV_adapter(context, myVM)
        }
    }

    //新增題目按鈕行為
    fun addQuestioBtnAction(){
        intentManager.putExtra("ToAddQuestion", "SelectedBank",  myVM.selectedBank.value)
        intentManager.putExtra("ToAddQuestion", "BankName",  myVM.bankName.value)
        startActivity(intentManager.getIntent("ToAddQuestion"))
    }

    //開始測驗按鈕行為
    fun testStartBtnAction(){
        intentManager.putExtra("ToQuestPage", "numExpect", getNumInput())
        startActivity(intentManager.getIntent("ToQuestPage"))
    }

    //取得輸入的數字
    fun getNumInput(): Int?{
        if (binding.numET1TypeNumOfQuestion.text.isNullOrEmpty()){
            myVM.numInput.value = myVM.questionsListNow.value?.size
        }else{
            myVM.numInput.value = binding.numET1TypeNumOfQuestion.text.toString().toInt()
        }
        return myVM.numInput.value
    }

    //Toast訊息整合
    fun toastManager(string: String, context: Context){
        var myString = ""
        when(string){
            "shuffledTitleSwitchAction_on" -> {"亂數題目啟動"}
            "shuffledTitleSwitchAction_ off" -> {"亂數題目關閉"}
            "shuffledQuestionSwitchAction_on" -> {"亂數選項啟動"}
            "shuffledQuestionSwitchAction_off" -> {"亂數選項關閉"}
            "btn2NumOfQuestionConfirm" -> {"題目數量已確認"}
        }
        Toast.makeText(context, myString, Toast.LENGTH_SHORT).show()
    }
}
