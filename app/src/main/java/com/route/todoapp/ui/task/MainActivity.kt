package com.route.todoapp.ui.task

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.route.todoapp.R
import com.route.todoapp.databinding.ActivityMainBinding
import com.route.todoapp.ui.fragments.bottomsheet.AddTodoFragment
import com.route.todoapp.ui.fragments.list.ListFragment
import com.route.todoapp.ui.fragments.settings.SettingsFragment
import com.route.todoapp.ui.update.UpdateTaskActivity
import com.route.todoapp.util.Utilities

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listFragment: ListFragment
    private val addTodoFragment =AddTodoFragment({
        listFragment.refreshRecyclerViewList()
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        listFragment= ListFragment {
            position,task->
            val intent = Intent(this, UpdateTaskActivity::class.java)
            intent.putExtra(Utilities.updatedTaskPosition,task.generatedID)
            startActivity(intent)
        }
        showFragment(listFragment)
    }
    private fun setupListeners() {
        binding.fab.setOnClickListener {

            addTodoFragment.show(supportFragmentManager,"addTodo")
        }
        binding.bottomNavigationButton.setOnItemSelectedListener ({
            when (it.itemId) {
                R.id.item_settings -> {
                    showFragment(SettingsFragment())
                    return@setOnItemSelectedListener false
                }

                R.id.item_tasks_list -> {
                    showFragment(listFragment)
                }
            }
            return@setOnItemSelectedListener true
        })
    }
    private fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }
}