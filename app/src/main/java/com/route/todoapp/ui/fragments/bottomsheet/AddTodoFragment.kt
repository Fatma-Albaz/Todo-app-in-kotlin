package com.route.todoapp.ui.fragments.bottomsheet

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todoapp.adapter.TaskAdapter
import com.route.todoapp.databinding.FragmentAddTodoBinding
import com.route.todoapp.model.TaskDM
import com.route.todoapp.repository.TaskRepository
import java.util.Calendar

class AddTodoFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTodoBinding
    private val calendar: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        bindDate()
        bindTime()
    }

    private fun bindDate() {
        binding.selectDateBtn.text = buildString {
            append("${calendar.get(Calendar.YEAR)}/")
            append("${calendar.get(Calendar.MONTH)+1} ")
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
        binding.addContactFab.setOnClickListener {
            if (!isValidData()) {
                return@setOnClickListener;
            }
            // Add task to recycler view list
            TaskRepository.addTask(
                TaskDM(
                    binding.taskNameLayout.editText?.text.toString().trim(),
                    binding.taskDescriptionLayout.editText?.text.toString().trim(),
                    binding.selectDateBtn.text.toString().trim(),
                    binding.selectTimeTv.text.toString().trim(),
                    false
                )
            )
            //TODO: Notify the list change
        }
        binding.selectTimeBtn.setOnClickListener{
            val picker = TimePickerDialog(requireContext(),
                { p0, hours, minutes ->
                    calendar.set(Calendar.HOUR_OF_DAY,hours)
                    calendar.set(Calendar.MINUTE,minutes)
                    bindTime()
                },calendar.get(Calendar.HOUR_OF_DAY),calendar[Calendar.MINUTE],true)
            picker.show()
        }
        binding.selectDateBtn.setOnClickListener{
            val picker = DatePickerDialog(requireContext(),
                { p0, p1, p2, p3 ->
                    calendar.set(Calendar.YEAR,p1)
                    calendar.set(Calendar.MONTH,p2)
                    calendar.set(Calendar.DAY_OF_MONTH,p3)
                    bindDate()
                },calendar.get(Calendar.YEAR),calendar[Calendar.MONTH],calendar[Calendar.DAY_OF_MONTH])
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