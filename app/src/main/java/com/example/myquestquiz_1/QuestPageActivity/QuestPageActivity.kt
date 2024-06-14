package com.example.myquestquiz_1.QuestPageActivity

import QuestPageViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myquestquiz_1.Manager.IntentManager
import com.example.myquestquiz_1.Manager.QuestionsOptionManager
import com.example.myquestquiz_1.Manager.ShuffleAndFillManager
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
    private lateinit var intentManager: IntentManager
    private lateinit var questPage_RV_AdapterSet: QuestPage_RV_AdapterSet
    private lateinit var questPage_AlertDialogSet: QuestPage_AlertDialogSet
    private lateinit var questPage_btnSet: QuestPage_btnSet
    private lateinit var questPageObserveSet: QuestPage_ObserveSet
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
        intentManager = IntentManager(this)
        questPage_RV_AdapterSet = QuestPage_RV_AdapterSet(binding, myVM, intentManager)
        questPage_AlertDialogSet = QuestPage_AlertDialogSet(this, myVM, intentManager)
        questPage_btnSet = QuestPage_btnSet(this, binding, myVM, intentManager, questPage_AlertDialogSet)
        questPageObserveSet = QuestPage_ObserveSet(binding, myVM, questPage_RV_AdapterSet, questPage_AlertDialogSet)


        CoroutineScope(Dispatchers.Main).launch {
            with(myVM) {
                joinAll(myVM.getVM_initJob())
                shuffledTitleSwitch_intent.value = intent.getBooleanExtra("ShuffledTitleSwitch", false)
                shuffledOption_intent.value = intent.getBooleanExtra("shuffledOption", false)
                seccondOrderList.value = shuffleAndFillManager.shufflerAndFill_indext(
                    (0 until listOfQuestions.value!!.size).toList(),
                    numExpect_intent.value!!,
                    intent.getBooleanExtra("ShuffledTitleSwitch", false)
                )
                binding.TV6ProgressMax.text = numExpect_intent.value.toString()

                progressControler.observe(this@QuestPageActivity) {
                    questPageObserveSet.progressControler_ObserveSet()
                }
                setContentView(binding.root)
            }
            questPage_btnSet.btn1ToNextQuestion_set()
        }
    }
}