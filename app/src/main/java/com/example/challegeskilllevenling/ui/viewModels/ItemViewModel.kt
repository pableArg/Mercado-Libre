package com.example.challegeskilllevenling.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challegeskilllevenling.data.ApiClient
import com.example.challegeskilllevenling.domain.category.CategoryItem
import com.example.challegeskilllevenling.domain.item.ItemResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ApiClient,
) : ViewModel() {

    val searchItem = MutableLiveData<List<ItemResponse>?>()
    private val searchCategory = MutableLiveData<List<CategoryItem>>()

    //consultar para sacar el bang bang y descargar el posible
    fun getCategories(query: String) {
        viewModelScope.launch {
            val response = repository.getCategories(query)
            if (response.isSuccessful && response.body() != null) {
                val items = response.body()!!
                items.first().let { it ->
                    val categoryId = it.category_id
                    getHighlight(categoryId)

                    Log.e("Paso category", it.category_id)
                }
            } else {
                Log.e("error", "No se pudo cargar la category ")
            }

        }

    }

    private suspend fun getHighlight(categoryId: String) {
        var productosIds: String = ""
        val response = repository.getHighlight(categoryId)
        if (response.isSuccessful && response.body() != null) {
            val items = response.body()!!
            items.content.forEach {
                if (it.type == "ITEM") {
                    productosIds += "${it.id},"
                    getItems(productosIds)

                }

            }

            Log.e("Pasó highlight", "paso highlight")
        } else {
            Log.e("Error", "No se pudo cargar highlight ")
        }
    }

    private suspend fun getItems(ids: String) {
        val response = repository.getItems(ids)
        if (response.isSuccessful) {
            val items = response.body()
            searchItem.value = items
            Log.e("que tiene items", searchItem.toString())
        }else {
            Log.e("no tiene items", searchItem.toString())

        }

    }


}