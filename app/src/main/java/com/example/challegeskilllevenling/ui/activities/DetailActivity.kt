package com.example.challegeskilllevenling.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.get
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.challegeskilllevenling.R
import com.example.challegeskilllevenling.databinding.ActivityDetailBinding
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "FAV_ITEMS")

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val list = mutableListOf<String>()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        renderItem()
        navigateToHome()
        getItem()?.body?.id?.let { onClickFav(it) }


    }

        /**
         *
        if the id of the item is found in the list the heart is painted red, otherwise it fades
         * */
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onClickFav(id: String) {
        binding.txtFavorite.setOnClickListener {
            drawable()
            if (list.contains(id)) {
                deleteItem(id)
            } else {
                insert(id)

            }
        }

    }

    private fun deleteItem(id: String) {
        list.remove(id)
    }

    /**
     * save object  id
     * */
    private fun insert(id: String) {
        list.add(id)
        CoroutineScope(Dispatchers.IO).launch {
            getItem()?.body.let {
                it?.let { it1 -> list.add(it1.id) }
                saveValues(
                    it?.id.toString(),
                    binding.txtTitle.text.toString(),
                    binding.txtPrice.text.toString()
                )

            }
        }
    }


    /**
     * save information the object  in format key - value
     * */
    private suspend fun saveValues(id: String, title: String, price: String) {
        dataStore.edit { preference ->
            preference[stringPreferencesKey("id")] = id
            preference[stringPreferencesKey("title")] = title
            preference[stringPreferencesKey("price")] = price

        }
    }


    /**
     *bind the object sent from the ItemAdapter with the xml
     * */
    @SuppressLint("SetTextI18n")
    private fun renderItem() {
        val item = getItem()
        with(binding) {
            txtTitle.text = item?.body?.titulo
            txtPrice.text = "$ ${item?.body?.precio.toString()}"
            txtSubTitle.text = item?.body?.titulo ?: item?.body?.titulo
            Picasso.get().load(item?.body?.imagen).into(imageView)
        }
    }

    /**
     * receives the object sent from the adapter
     * */
    private fun getItem(): ItemResponse? {
        return intent.getParcelableExtra("item_data")
    }

    /**
     * return to main activity
     * */
    private fun navigateToHome() {
        binding.imgBack.setOnClickListener {
            val intent =
                Intent(this, MainActivity::class.java).addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun drawable() {
        getItem()?.body.let {
            if (list.contains(it?.id)) {
                binding.imgFav.background = getDrawable(R.drawable.ic_fav_pressed)
            } else {
                binding.imgFav.background = getDrawable(R.drawable.ic_fav_default)

            }
        }

    }


}