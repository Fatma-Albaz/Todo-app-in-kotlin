package com.route.todoapp.ui.fragments.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.route.todoapp.adapter.TaskAdapter
import com.route.todoapp.databinding.FragmentListBinding
import com.route.todoapp.repository.TaskRepository

class ListFragment : Fragment() {
    lateinit var binding: FragmentListBinding
    var adapter: TaskAdapter = TaskAdapter(TaskRepository.generateDummyList())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclerView() {
        binding.taskRecyclerView.adapter = adapter
        adapter.onListUpdated()
        //var onClick= ItemClickListener()
    }
}