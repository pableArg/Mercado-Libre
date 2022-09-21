package com.example.challegeskilllevenling.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challegeskilllevenling.databinding.ActivityMainBinding
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.adapter.ItemAdapter
import com.example.challegeskilllevenling.ui.viewModels.ItemViewModel
import com.example.challegeskilllevenling.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


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
        onClickFav()

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
           snackBar()
        }

    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this,manager.orientation)
        binding.rv.layoutManager = manager
        adapter = ItemAdapter(applicationContext,itemList)
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(decoration)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.searchItem.observe(this) {
            if (it.isNullOrEmpty()) {
                snackBar()
            }else {
                adapter.itemList = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun onClickFav(){
        binding.imFav.setOnClickListener {
            val intent = Intent (applicationContext, FavActivity::class.java)
            startActivity(intent)
        }
    }



    private fun snackBar() {
        Snackbar.make(binding.activityConstraint,"Error al cargar los Items" , Snackbar.LENGTH_LONG).show()
    }


}