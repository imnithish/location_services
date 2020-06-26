package com.imnstudios.riafytest.workmanagertest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class ChainWorkerOne(context: Context, params: WorkerParameters) : Worker(context, params) {

    @SuppressLint("SimpleDateFormat")
    override fun doWork(): Result {

        try {

            for (i in 0..3000) {
                Log.i("MYTAG", "chainworker 1 $i")
            }

            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = time.format(Date())
            Log.i("MYTAG", currentDate)
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}