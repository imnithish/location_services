package com.imnstudios.riafytest.workmanagertest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ChainWorkerTwo(context: Context, params: WorkerParameters) : Worker(context, params) {

    @SuppressLint("SimpleDateFormat")
    override fun doWork(): Result {

        try {

            for (i in 0 .. 300) {
                Log.i("MYTAG", "chainworker 2 $i")
            }

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}