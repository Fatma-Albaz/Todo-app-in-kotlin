package com.route.todoapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class TaskDM (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo
    var taskName: String,
    @ColumnInfo
    var taskDescription:String,
    @ColumnInfo
    var taskDate: String,
    @ColumnInfo
    var taskTime: String,
    @ColumnInfo
    var isDone: Boolean, ) : Serializable