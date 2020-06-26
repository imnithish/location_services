package com.imnstudios.riafytest.workmanagertest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ParallelWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    @SuppressLint("SimpleDateFormat")
    override fun doWork(): Result {

        try {

            for (i in 0 .. 3000) {
                Log.i("MYTAG", "parallelworker 2 $i")
            }

            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}