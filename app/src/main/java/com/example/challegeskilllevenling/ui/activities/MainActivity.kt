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
        navigateToFav()

    }

    /**
     * through a query call getCategories viewModel*/

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

    /**
     * starts recyclerview sending the list loaded by the observer , also puts a decoration
     * between the lines
     * */
    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rv.layoutManager = manager
        adapter = ItemAdapter(applicationContext, itemList)
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(decoration)
    }

    /**
     *watch for changes to the list that loads the viewmodel .
     * If this list is empty , it will show a snacbar , instead if it has information ,
     * it will fill the list that is passed to the adapter and notify it
     * */
    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewModel.searchItem.observe(this) {
            if (it.isNullOrEmpty()) {
                snackBar()
            } else {
                adapter.itemList = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun snackBar() {
        Snackbar.make(binding.activityConstraint, "Error al cargar los Items", Snackbar.LENGTH_LONG)
            .show()
    }

    private fun navigateToFav() {
        binding.imFav.setOnClickListener {
            val intent =
                Intent(this, FavActivity::class.java)
            startActivity(intent)
        }
    }


}