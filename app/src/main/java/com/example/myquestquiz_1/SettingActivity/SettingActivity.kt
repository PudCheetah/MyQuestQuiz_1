package com.example.myquestquiz_1.SettingActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myquestquiz_1.Manager.IntentManager
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
    private lateinit var btnSet: Setting_btnSet
    private lateinit var rvAdapterset: Setting_RV_AdapterSet
    private lateinit var switchSet: Setting_switchSet
    private lateinit var toastManager: Setting_toastManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreatJob = CoroutineScope(Dispatchers.Main).launch {
            binding = ActivitySettingBinding.inflate(layoutInflater)
            myVM = ViewModelProvider(this@SettingActivity).get(SettingActivityViewModel::class.java)
            intentManager = IntentManager(this@SettingActivity)
            toastManager = Setting_toastManager()
            btnSet = Setting_btnSet(this@SettingActivity, binding, myVM, intentManager, toastManager)
            rvAdapterset = Setting_RV_AdapterSet(this@SettingActivity, binding, myVM)
            switchSet = Setting_switchSet(toastManager, intentManager)

            joinAll(myVM.getVMinitJob())
            setContentView(binding.root)
            binding.switch1IsShuffledTitle.setOnCheckedChangeListener { buttonView, isChecked ->
                myVM.shuffledTitleSwitch.value = isChecked
                switchSet.shuffledTitleSwitchAction(isChecked, this@SettingActivity)
            }
            binding.switch2IsShuffledOption.setOnCheckedChangeListener { buttonView, isChecked ->
                myVM.shuffledOptionSwitch.value = isChecked
                switchSet.shuffledOptionSwitchAction(isChecked, this@SettingActivity)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).launch {
            joinAll(onCreatJob)
            myVM.selectedBank.value = intent.getLongExtra("bankID", -1)
            myVM.bankName.value = intent.getStringExtra("BankName")
            binding.TV6BankID.text = ("題庫ID: " + myVM.selectedBank.value.toString())
            binding.TV7BankName.text = ("題庫名稱: " + myVM.bankName.value)
            joinAll(myVM.updateQuestionsListNow(myVM.selectedBank.value!!))
            myVM.updateQuestionTotl(myVM.questionsListNow.value)
            myVM.questionsListNow.observe(this@SettingActivity){
                binding.TV5NumOfBankNum.text = myVM.questionsListNow.value?.size.toString()
                rvAdapterset.adapterSetting()
            }
            binding.switch1IsShuffledTitle.isChecked = myVM.shuffledTitleSwitch.value ?: true
            binding.switch2IsShuffledOption.isChecked = myVM.shuffledOptionSwitch.value ?: true
            btnSet.btn3ToAddQuestionPage_set()
            btnSet.btn2NumOfQuestionConfirm_set()
            btnSet.btn1ToQuestPage_set()
        }
    }


}
