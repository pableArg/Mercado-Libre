package com.example.challegeskilllevenling.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challegeskilllevenling.data.database.service.ApiClient
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ApiClient,
) : ViewModel() {

    val searchItem = MutableLiveData<List<ItemResponse>?>()
    val status = MutableLiveData<ApiStatus>()


    fun getCategories(query: String) {
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                val response = repository.getCategories(query)
                if (response.isNullOrEmpty()) {
                    status.value = ApiStatus.FAILURE
                    searchItem.value  = emptyList()
                } else {
                    searchItem.value = response
                    status.value = ApiStatus.SUCCESS
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }
    }



}