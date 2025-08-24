package com.route.todoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.route.todoapp.database.entity.TaskDM

@Dao
interface TaskDao {
    @Update
    fun updateTask (taskDM: TaskDM)

    @Insert
    fun insertTask (taskDM: TaskDM) : Long

    @Delete
    fun deleteTask (taskDM: TaskDM)

    @Query("SELECT * FROM TaskDM")
    fun getAll(): List<TaskDM>

    @Query("SELECT * FROM TaskDM WHERE taskDate LIKE :date ")
    fun findByDate(date: String): TaskDM
}