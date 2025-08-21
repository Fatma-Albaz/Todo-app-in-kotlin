package com.route.todoapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todoapp.R
import com.route.todoapp.databinding.ItemTaskBinding
import com.route.todoapp.model.TaskDM

class TaskAdapter(private var listOfTasks: MutableList<TaskDM>) : Adapter<TaskAdapter.TaskViewHolder> () {

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
        fun onTaskClickListener(task:TaskDM,position: Int)
        fun onIsDoneClickListener(task:TaskDM,position: Int)
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
           /*else {
                binding.btnTaskDone.setImageResource(R.drawable.ic_check)
                binding.btnTaskDone.setBackgroundColor(R.drawable.bg_rounded_blue)
                binding.verticalBorder.setBackgroundColor(R.drawable.bg_rounded_blue)
                binding.taskNameEt.setTextColor(Color.parseColor("#FF5D9CEC")) // blue
            }*/

            binding.btnTaskDone.setOnClickListener{
                onClickListener?.onIsDoneClickListener(task,adapterPosition)
            }
            binding.root.setOnClickListener{
                onClickListener?.onTaskClickListener(task,adapterPosition)
            }
        }
    }
    public fun onItemUpdated(position: Int){
        notifyItemChanged(position)
    }
    @SuppressLint("NotifyDataSetChanged")
    public fun onListUpdated(){
        notifyDataSetChanged()
    }
}