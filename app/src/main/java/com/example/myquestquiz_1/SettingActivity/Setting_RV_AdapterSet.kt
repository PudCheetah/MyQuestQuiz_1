package com.example.myquestquiz_1.SettingActivity

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquestquiz_1.RVadapter.SettingActivity_RV_adapter
import com.example.myquestquiz_1.databinding.ActivitySettingBinding

class Setting_RV_AdapterSet(var context: Context, var binding: ActivitySettingBinding, var myVM: SettingActivityViewModel) {
    //RV設定
    fun adapterSetting(){
        with(binding.RV1RvForQuestions){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = SettingActivity_RV_adapter(context, myVM)
        }
    }
}