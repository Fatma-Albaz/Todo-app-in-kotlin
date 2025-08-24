package com.route.todoapp.ui.fragments.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.route.todoapp.adapter.TaskAdapter
import com.route.todoapp.database.AppDatabase
import com.route.todoapp.database.entity.TaskDM
import com.route.todoapp.databinding.FragmentListBinding

class ListFragment(var onTaskUpdateClickListener:(position:Int, taskDm:TaskDM)->Unit) : Fragment() {
    private lateinit var binding: FragmentListBinding
    private var adapter= TaskAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshRecyclerViewList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        refreshRecyclerViewList()
    }

    public fun refreshRecyclerViewList() {
        val todoList = AppDatabase.getInstance().getDao().getAll()
        if (todoList.isNotEmpty()){
            adapter.updateList(todoList)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclerView() {
        binding.taskRecyclerView.adapter = adapter
        adapter.onClickListener = object :TaskAdapter.ItemClickListener{
            override fun onTaskClickListener(task: TaskDM, position: Int) {
                onTaskUpdateClickListener(position, task)
            }

            override fun onIsDoneClickListener(task: TaskDM, position: Int) {
                task.isDone = true
                AppDatabase.getInstance().getDao().updateTask(task)
                refreshRecyclerViewList()
            }

            override fun onDeleteButtonClickListener(task: TaskDM, position: Int) {
                AppDatabase.getInstance().getDao().deleteTask(task)
                //adapter.onItemUpdated(position)
                adapter.onTaskDeleted(AppDatabase.getInstance().getDao().getAll(),position)
            }
        }
        //var onClick= ItemClickListener()
    }
}