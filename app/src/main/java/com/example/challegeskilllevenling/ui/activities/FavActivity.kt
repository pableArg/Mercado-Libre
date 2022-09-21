package com.example.challegeskilllevenling.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challegeskilllevenling.databinding.ActivityFavBinding

import com.example.challegeskilllevenling.domain.item.ItemFav
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.adapter.FavAdapter
import com.example.challegeskilllevenling.ui.viewModels.FavViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavActivity : AppCompatActivity() {
    private val viewModel: FavViewModel by viewModels()
    private lateinit var binding: ActivityFavBinding
    private lateinit var adapter: FavAdapter
    private val itemList = mutableListOf<ItemResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getItemId()
        initRecyclerView()
        setupObserver()



    }

    private fun getItemId() {
        lifecycleScope.launch(Dispatchers.IO) {
            getItem().collect() {
                val id = it.id
               // itemId = id
               // Log.e("itemid" , itemId)
            }
        }
    }



    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this,manager.orientation)
        binding.rv.layoutManager = manager
        adapter = FavAdapter(itemList)
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(decoration)
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserver() {
        viewModel.itemFav.observe(this) {
            if (it.isNullOrEmpty()) {
        Log.e("error al cargar id", "error la cargar el id")
            } else {
               adapter.itemList
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun getItem() = dataStore.data.map { preferences ->
        ItemFav(
            id = preferences[stringPreferencesKey("id")].orEmpty(),

            )
    }

}



