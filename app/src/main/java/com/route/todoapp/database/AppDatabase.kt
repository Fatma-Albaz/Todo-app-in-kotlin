package com.route.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.todoapp.database.dao.TaskDao
import com.route.todoapp.database.entity.TaskDM

@Database(entities = [TaskDM::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): TaskDao

    companion object {
        private var database: AppDatabase? = null
        fun initDatabase(context: Context) {
            if (database == null) {
                database = Room.databaseBuilder(
                    context, AppDatabase::class.java, "tasks-database"
                ).fallbackToDestructiveMigration(true)
                    .allowMainThreadQueries()
                    .build()
            }
        }

        fun getInstance(): AppDatabase {
            return database!!
        }
    }
}