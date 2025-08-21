package com.route.todoapp.ui.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.route.todoapp.R
import com.route.todoapp.databinding.ActivityMainBinding
import com.route.todoapp.ui.fragments.bottomsheet.AddTodoFragment
import com.route.todoapp.ui.fragments.list.ListFragment
import com.route.todoapp.ui.fragments.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listFragment: ListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        listFragment= ListFragment()
        showFragment(listFragment)
    }
    private fun setupListeners() {
        binding.fab.setOnClickListener {
            val addTodoFragment =AddTodoFragment()
            addTodoFragment.show(supportFragmentManager,"addTodo")
        }
        binding.bottomNavigationButton.setOnItemSelectedListener ({
            when (it.itemId) {
                R.id.item_settings -> {
                    showFragment(SettingsFragment())
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