package com.example.labtest3.utils

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.labtest3.R


class NotificationHelper(private val context: Context) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showBudgetAlert() {
        val builder = NotificationCompat.Builder(context, "budget_alert_channel")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Budget Limit Exceeded!")
            .setContentText("Your expenses exceeded the budget.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val manager = NotificationManagerCompat.from(context)
        manager.notify(101, builder.build())
    }
}
