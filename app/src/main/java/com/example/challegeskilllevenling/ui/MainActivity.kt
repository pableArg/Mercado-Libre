package com.example.challegeskilllevenling.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challegeskilllevenling.databinding.ActivityMainBinding
import com.example.challegeskilllevenling.domain.item.Item
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.adapter.ItemAdapter
import com.example.challegeskilllevenling.ui.viewModels.ItemViewModel
import com.example.challegeskilllevenling.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ItemViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private val itemList = mutableListOf<ItemResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSearchViewListener()
        initRecyclerView()
        setupObservers()

    }




    private fun setSearchViewListener() {
        try {
            binding.sv.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.run {
                            viewModel.getCategories(this)
                        }
                        hideKeyboard()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })

        } catch (e: Exception) {
            Log.d(TAG, "e")
        }

    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this,manager.orientation)
        binding.rv.layoutManager = manager
        adapter = ItemAdapter(applicationContext,itemList) { onItemSelected(it) }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(decoration)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.searchItem.observe(this) {
            if (it != null) {
                adapter.itemList = it
                adapter.notifyDataSetChanged()
            }


        }
    }
    fun onItemSelected(item : Item){


    }


}