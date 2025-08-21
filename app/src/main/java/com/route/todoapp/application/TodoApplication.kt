package com.route.todoapp.application

import android.app.Application
import com.route.todoapp.database.AppDatabase

class TodoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)
    }
}