package com.route.todoapp.ui.update

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import com.route.todoapp.adapter.TaskAdapter
import com.route.todoapp.database.AppDatabase
import com.route.todoapp.database.entity.TaskDM
import com.route.todoapp.databinding.ActivityUpdateTaskBinding
import com.route.todoapp.util.Utilities
import java.util.Calendar


class UpdateTaskActivity : Activity() {
    lateinit var taskDM: TaskDM
    private lateinit var binding: ActivityUpdateTaskBinding
    private val calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val task= intent.getSerializableExtra(Utilities.updatedTaskPosition) as TaskDM
        if(task==null){
            finish()
        }
        taskDM = task
        initListeners()
        initViews()
    }

    private fun initViews() {
        binding.selectDateBtn.text= taskDM.taskDate
        binding.selectTimeBtn.text= taskDM.taskTime
        binding.taskNameLayout.editText?.setText(taskDM.taskName)
        binding.taskDescriptionLayout.editText?.setText(taskDM.taskDescription)
    }

    private fun bindDate() {
        binding.selectDateBtn.text = buildString {
            append("${calendar.get(Calendar.YEAR)}/")
            append("${calendar.get(Calendar.MONTH) + 1} ")
            append("/ ${calendar.get(Calendar.DAY_OF_MONTH)}")
        }
    }

    private fun bindTime() {
        binding.selectTimeBtn.text = buildString {
            append(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)))
            append(":")
            append(String.format("%02d", calendar.get(Calendar.MINUTE)))
        }
    }

    private fun initListeners() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.updateTaskBtn.setOnClickListener {
            if (!isValidData()) {
                return@setOnClickListener;
            }
            taskDM.taskName= binding.taskNameLayout.editText?.text.toString().trim()
            taskDM.taskDescription= binding.taskDescriptionLayout.editText?.text.toString().trim()
            taskDM.taskDate= binding.selectDateBtn.text.toString().trim()
            taskDM.taskTime= binding.selectTimeBtn.text.toString().trim()
            AppDatabase.getInstance().getDao().updateTask(
                taskDM
            )
            // Add task to recycler view list
            /*TaskRepository.addTask(
                TaskDM(
                    binding.taskNameLayout.editText?.text.toString().trim(),
                    binding.taskDescriptionLayout.editText?.text.toString().trim(),
                    binding.selectDateBtn.text.toString().trim(),
                    binding.selectTimeTv.text.toString().trim(),
                    false
                )*/
            finish()
        }
        binding.selectTimeBtn.setOnClickListener {
            val picker = TimePickerDialog(this,
                { p0, hours, minutes ->
                    calendar.set(Calendar.HOUR_OF_DAY, hours)
                    calendar.set(Calendar.MINUTE, minutes)
                    bindTime()
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar[Calendar.MINUTE], true
            )
            picker.show()
        }
        binding.selectDateBtn.setOnClickListener {
            val picker = DatePickerDialog(this,
                { p0, p1, p2, p3 ->
                    calendar.set(Calendar.YEAR, p1)
                    calendar.set(Calendar.MONTH, p2)
                    calendar.set(Calendar.DAY_OF_MONTH, p3)
                    bindDate()
                },
                calendar.get(Calendar.YEAR),
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
            picker.show()
        }
    }

    private fun isValidData(): Boolean {
        var isValid = true
        if (binding.taskNameLayout.editText?.text.toString().isEmpty()) {
            binding.taskNameLayout.editText?.error = "Task name is required"
            isValid = false
        } else {
            binding.taskNameLayout.editText?.error = null
        }
        if (binding.taskDescriptionLayout.editText?.text.toString().isEmpty()) {
            binding.taskDescriptionLayout.editText?.error = "Task description is required"
            isValid = false
        } else {
            binding.taskDescriptionLayout.editText?.error = null
        }
        return isValid
    }

}