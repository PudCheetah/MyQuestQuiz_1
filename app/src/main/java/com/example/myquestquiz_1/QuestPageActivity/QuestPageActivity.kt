package com.example.myquestquiz_1.QuestPageActivity

import QuestPageViewModel
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myquestquiz_1.Manager.QuestionsOptionManager
import com.example.myquestquiz_1.Manager.ShuffleAndFillManager
import com.example.myquestquiz_1.MyDatabase.Question
import com.example.myquestquiz_1.RVadapter.QuestPageActivity_RV_adapter
import com.example.myquestquiz_1.databinding.ActivityQuestPageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class QuestPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestPageBinding
    private lateinit var myVM: QuestPageViewModel
    private lateinit var questionsOptionManager: QuestionsOptionManager
    private lateinit var shuffleAndFillManager: ShuffleAndFillManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestPageBinding.inflate(layoutInflater)
        myVM = ViewModelProvider(
            this,
            QuestPageViewModelFactory(
                application,
                intent.getLongExtra("bankID", -1),
                intent.getIntExtra("numExpect", -1)
            )
        ).get(QuestPageViewModel::class.java)
        questionsOptionManager = QuestionsOptionManager()
        shuffleAndFillManager = ShuffleAndFillManager()

        CoroutineScope(Dispatchers.Main).launch {
            joinAll(myVM.getVM_initJob())
            myVM.shuffledTitleSwitch_intent.value =
                intent.getBooleanExtra("ShuffledTitleSwitch", false)
            myVM.shuffledOption_intent.value = intent.getBooleanExtra("shuffledOption", false)
            myVM.seccondOrderList.value = shuffleAndFillManager.shufflerAndFill_indext(
                (0 until myVM.listOfQuestions.value!!.size).toList(),
                myVM.numExpect_intent.value!!,
                intent.getBooleanExtra("ShuffledTitleSwitch", false)
            )
            binding.TV6ProgressMax.text = myVM.numExpect_intent.value.toString()
            myVM.progressControler.observe(this@QuestPageActivity){
                //numInSeccondOrderList代表亂數列表中的第(${myVM.progressControler.value}+1)個數字
                var numInSeccondOrderList = myVM.seccondOrderList.value!!.get(myVM.progressControler.value!!)
                binding.TV2QuetionTitle.text = myVM.listOfQuestions.value!![numInSeccondOrderList].questionTitle
                binding.TV4ProgressNow.text = (myVM.progressControler.value!! + 1).toString()
                rvAdapterSet(myVM, myVM.listOfQuestions.value!![numInSeccondOrderList])
            }
            binding.FAB1ToNextPageOfQuestion.setOnClickListener {
                if (myVM.numExpect_intent.value!! > (myVM.progressControler.value!! + 1)){
                    myVM.progressControler.value = myVM.progressControler.value!!.plus(1)
                    Log.d("myTag", "numExpect_intent ${myVM.numExpect_intent.value}")
                    Log.d("myTag", "myVM.progressControler.value : ${myVM.progressControler.value} ")
                    binding.root.invalidate()
                }else{
                    Toast.makeText(this@QuestPageActivity, "已經到底了", Toast.LENGTH_SHORT).show()
                }
            }




            with(myVM) {
                Log.d("myTag", "innerList.value: ${seccondOrderList.value}")
                Log.d("myTag", "listOfQuestions: ${listOfQuestions.value}")
                Log.d("myTag", "numExpect_intent: ${numExpect_intent.value}")
                Log.d("myTag", "bankID_intent: ${bankID_intent.value}")
                Log.d("myTag", "shuffledTitleSwitch_intent: ${shuffledTitleSwitch_intent.value}")
                Log.d("myTag", "shuffledOption_intent: ${shuffledOption_intent.value}")
            }
            setContentView(binding.root)
        }


    }
    fun rvAdapterSet(myVM: QuestPageViewModel,question: Question){
        with(binding.RV1ForOption){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = QuestPageActivity_RV_adapter(myVM, question)
        }
    }
}