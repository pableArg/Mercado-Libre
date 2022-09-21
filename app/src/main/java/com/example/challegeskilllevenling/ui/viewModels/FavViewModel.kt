package com.example.challegeskilllevenling.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challegeskilllevenling.data.database.service.ApiClient
import com.example.challegeskilllevenling.domain.item.ItemResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    private val repository: ApiClient,

    ) : ViewModel() {

   val itemFav = MutableLiveData<List<ItemResponse>?>()



         fun updateItem(id : String){
            viewModelScope.launch {
                val response = repository.getItems(id)
                if (response.isNotEmpty()){
                    itemFav.value = response
                }
            }
        }



}