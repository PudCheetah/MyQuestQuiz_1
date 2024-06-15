package com.example.myquestquiz_1.StartActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquestquiz_1.MyDatabase.MyQuestionBank
import com.example.myquestquiz_1.R
import com.example.myquestquiz_1.databinding.ActivityStartBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch


//起始頁面
class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var myVM: StartActivityViewModel
    private var myBankList: List<MyQuestionBank>?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        myVM = ViewModelProvider(this@StartActivity).get(StartActivityViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            joinAll(myVM.getStartActivityViewModel_InitJob())
            myVM.lisOfBank.observe(this@StartActivity){
                startActivity_RV_adapterSet()
                binding.root.invalidate()
            }
        }
        setContentView(binding.root)
    }

    //新增題庫
    fun btn_1_activityStartOnclick(view: View){
        if(binding.ET1TypeBankName.text?.isEmpty() == true){
            Toast.makeText(this, getString(R.string.ErrorBankNameCantBeEmpty), Toast.LENGTH_SHORT).show()
        }else{
            myVM.upsertBank(binding.ET1TypeBankName.text.toString())
            binding.ET1TypeBankName.setText("")
        }
    }

    //RV設定
    fun startActivity_RV_adapterSet(){
        with(binding.RV1RvForQuestionBank){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = StartActivity_RV_Adapter(context, myVM)
        }
    }
}