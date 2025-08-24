package com.route.todoapp.repository

import com.route.todoapp.database.entity.TaskDM

class TaskRepository {
    companion object{
        private var listOfTasks: MutableList<TaskDM> = arrayListOf()
        fun generateDummyList (): MutableList<TaskDM>{
            var localInstance: TaskDM
            for (i in 0..100){
                localInstance = TaskDM(i,"Task $i Name", "Task $i Description"
                ,"$i/10/2025","11:11",false)
                listOfTasks.add(localInstance)
            }
            return listOfTasks
        }
        fun addTask (addedTaskDM: TaskDM): Boolean{
            for (i in 0..<listOfTasks.size){
                if( addedTaskDM.taskName.equals(listOfTasks[i].taskName)){
                    return false
                }
            }
            listOfTasks.add(addedTaskDM)
            return true
        }

        fun deleteTask (addedTaskDM: TaskDM): Boolean{
            for (i in 0..<listOfTasks.size){
                if( addedTaskDM.taskName.equals(listOfTasks[i].taskName)){
                    listOfTasks.remove(addedTaskDM)
                    return true
                }
            }
           return false
        }
    }
}