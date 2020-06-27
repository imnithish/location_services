package com.imnstudios.riafytest.workmanagertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.imnstudios.riafytest.R
import com.imnstudios.riafytest.utils.toast
import kotlinx.android.synthetic.main.activity_main_three.*
import java.util.concurrent.TimeUnit

class MainActivityThree : AppCompatActivity() {
    companion object {
        const val KEY_COUNT_VALUE = "key_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_three)
        button.setOnClickListener {
//            setOneTimeWorkRequest()
            setPeriodicWorkRequest()
        }

    }

    private fun setOneTimeWorkRequest() {
        val workManager = WorkManager.getInstance(applicationContext)
        val data: Data = Data.Builder()
            .putInt(KEY_COUNT_VALUE, 125)
            .build()
        val constraints = Constraints.Builder()
//            .setRequiresCharging(true)
            .build()
        val oneTimeWorkRequest: OneTimeWorkRequest =
            OneTimeWorkRequest.Builder(WorkerClass::class.java)
                .setConstraints(constraints)
                .setInputData(data)
                .build()


        val chainWorkerOneRequest: OneTimeWorkRequest =
            OneTimeWorkRequest.Builder(ChainWorkerOne::class.java).build()

        val chainWorkerTwoRequest: OneTimeWorkRequest =
            OneTimeWorkRequest.Builder(ChainWorkerTwo::class.java).build()


        val parallelWorkerRequest: OneTimeWorkRequest =
            OneTimeWorkRequest.Builder(ParallelWorker::class.java).build()

        //for chaining use mutable list
        val parallelWorks = mutableListOf<OneTimeWorkRequest>()
        parallelWorks.add(parallelWorkerRequest)
        parallelWorks.add(chainWorkerOneRequest)

        workManager.beginWith(parallelWorks)
            .then(chainWorkerOneRequest)
            .then(chainWorkerTwoRequest).enqueue()
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this, Observer {
                textView.text = it.state.name
                if (it.state.isFinished) {
                    val data = it.outputData
                    val message = data.getString(WorkerClass.KEY_WORKER)
                    toast(message.toString())
                }
            })
    }

    //periodic work request
    private fun setPeriodicWorkRequest() {
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(ChainWorkerTwo::class.java, 16, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
    }
}