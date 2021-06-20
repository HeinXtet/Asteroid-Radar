package com.deevvdd.asteroidradar.wok

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deevvdd.asteroidradar.domain.repository.AsteroidRepository
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 20,June,2021
 */
class DeleteAsteroidWork(
    context: Context,
    params: WorkerParameters,
    private val repository: AsteroidRepository

) : CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "DeleteAsteroidWork"
    }

    override suspend fun doWork(): Result {
        Timber.d("DO WORK SUCCESS DeleteAsteroidWork")
        return try {
            repository.deleteAsteroidBeyondToday()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}