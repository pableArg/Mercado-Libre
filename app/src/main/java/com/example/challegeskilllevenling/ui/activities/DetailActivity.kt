package com.example.challegeskilllevenling.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        onClickFav()


    }





    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onClickFav() {
        binding.txtFavorite.setOnClickListener {
            if (!list.contains(getItem()?.body?.id)) {
                list.add(it.id.toString())
                insert()
                binding.imgFav.background = getDrawable(R.drawable.ic_fav_pressed)
            } else {
                list.remove(it.id.toString())
                binding.imgFav.background = getDrawable(R.drawable.ic_fav_default)
                delete()
            }
        }
    }



    private fun insert() {
        CoroutineScope(Dispatchers.IO).launch {
            getItem()?.body?.let {
                saveValues(
                    getItem()?.body?.id ?: binding.txtTitle.text.toString()

                )
            }
        }
    }

    private fun delete() {
        CoroutineScope(Dispatchers.IO).launch {
            deleteValues()
        }
    }

    private suspend fun saveValues(id: String) {
        dataStore.edit { preference ->
            preference[stringPreferencesKey("id")] = id
        }
    }

    private suspend fun deleteValues() {
        dataStore.edit { preference ->
            preference.clear()
        }
    }

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

    private fun getItem(): ItemResponse? {
        return intent.getParcelableExtra("item_data")
    }


    private fun navigateToHome() {
        binding.imgBack.setOnClickListener {
            val intent =
                Intent(this, MainActivity::class.java).addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }
    }


}