package com.route.todoapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todoapp.R
import com.route.todoapp.databinding.ItemTaskBinding
import com.route.todoapp.database.entity.TaskDM

class TaskAdapter(var listOfTasks: List<TaskDM>) : Adapter<TaskAdapter.TaskViewHolder> () {

    private lateinit var binding: ItemTaskBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindItem(listOfTasks[position])
    }

    override fun getItemCount(): Int = listOfTasks.size

    interface ItemClickListener{
        fun onTaskClickListener(task: TaskDM, position: Int)
        fun onIsDoneClickListener(task: TaskDM, position: Int)
        fun onDeleteButtonClickListener(task: TaskDM, position: Int)
    }

    var onClickListener: ItemClickListener? = null

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root){

        public fun bindItem (task: TaskDM){
            binding.taskNameEt.text = task.taskName
            binding.taskTimeEt.text = task.taskTime
            if (task.isDone){
                binding.btnTaskDone.setImageResource(R.drawable.ic_done)
                binding.btnTaskDone.setBackgroundColor(R.drawable.bg_rounded_white)
                binding.verticalBorder.setBackgroundColor(R.drawable.bg_rounded_green)
                binding.taskNameEt.setTextColor(Color.parseColor("#FF61E757")) // green
            }

            binding.btnTaskDone.setOnClickListener{
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION){
                    onClickListener?.onIsDoneClickListener(task,bindingAdapterPosition)
                }
            }

            binding.dragItemLayout.setOnClickListener{
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onClickListener?.onTaskClickListener(task, bindingAdapterPosition)
                }
            }
            binding.deleteBtn.setOnClickListener{
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION){
                    onClickListener?.onDeleteButtonClickListener(task,bindingAdapterPosition)
                }
            }
        }
    }
    public fun onItemUpdated(position: Int){
        notifyItemChanged(position)
    }
    public fun updateList(tasks:List<TaskDM>){
        listOfTasks = tasks
        onListUpdated(listOfTasks)
    }
    @SuppressLint("NotifyDataSetChanged")
    public fun onTaskDeleted(tasks: List<TaskDM>, position: Int){
        listOfTasks = tasks
        notifyItemRemoved(position)
    }
    @SuppressLint("NotifyDataSetChanged")
    public fun onListUpdated(listOfTasks: List<TaskDM>){
        notifyDataSetChanged()
        for (task in listOfTasks){
            if (task.isDone){
                binding.btnTaskDone.setImageResource(R.drawable.ic_done)
                binding.btnTaskDone.setBackgroundColor(R.drawable.bg_rounded_white)
                binding.verticalBorder.setBackgroundColor(R.drawable.bg_rounded_green)
                binding.taskNameEt.setTextColor(Color.parseColor("#FF61E757")) // green
            }
        }
    }
}