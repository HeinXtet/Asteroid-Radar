package com.deevvdd.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.deevvdd.asteroidradar.wok.DeleteAsteroidWork
import com.deevvdd.asteroidradar.wok.RefreshAsteroidWork
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */
@HiltAndroidApp
class AsteriodApp : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        val config = Configuration.Builder()
            .build()
        WorkManager.initialize(this, config)
        delayedInit()
    }

    private fun setupRecurringWork() {

        // delete work
        val constraintsDelete = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequestDelete =
            PeriodicWorkRequestBuilder<RefreshAsteroidWork>(1, TimeUnit.MINUTES)
                .setConstraints(constraintsDelete)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            DeleteAsteroidWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequestDelete
        )

        // refresh work
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshAsteroidWork>(1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshAsteroidWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }
}