package com.example.challegeskilllevenling.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Size

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challegeskilllevenling.data.database.service.ApiClient
import com.example.challegeskilllevenling.databinding.ActivityFavBinding

import com.example.challegeskilllevenling.domain.item.ItemFav
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.adapter.FavAdapter
import com.example.challegeskilllevenling.ui.viewModels.FavViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavBinding
    private val list = mutableListOf<String>()
    private lateinit var repository : ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)


        render()

        lifecycleScope.launch {
           val response =  repository.getItems(list.toString())
        }
        Log.e("lista",list.toString())


    }

    /**
     * binding xml with data
     * */
    private fun render() {
        lifecycleScope.launch {
            getItem().collect {
                binding.txtTitle.text = it.title
                binding.txtId.text = it.id
                binding.txtPrice.text = it.price
                list.add(it.id)
            }
        }
    }

    /**
     * recupera la informacion enviada retrieve the sent information
     * */
    private fun getItem() = dataStore.data.map { preferences ->
        ItemFav(
            id = preferences[stringPreferencesKey("id")].orEmpty(),
            title = preferences[stringPreferencesKey("title")].orEmpty(),
            price = preferences[stringPreferencesKey("price")].orEmpty(),
        )
    }

    private suspend fun deleteValues() {
        dataStore.edit { preference ->
            preference.clear()
        }
    }


}



