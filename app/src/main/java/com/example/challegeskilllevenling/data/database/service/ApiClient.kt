package com.example.challegeskilllevenling.data.database.service

import androidx.lifecycle.MutableLiveData
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.ApiStatus
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val api: MercadoApi
) {
    val status = MutableLiveData<ApiStatus>()

    /*Hits the getCategory endpoint, iterates over the first position and
    * retrieves the categoryId and passes it to getHighLight
    *  */
    suspend fun getCategories(q: String): List<ItemResponse> {
        val response = api.getCategory(q)
        var categoryId = ""
        if (response.isNotEmpty()) {
            response.first().let { it ->
                categoryId = it.category_id
                status.value = ApiStatus.SUCCESS
            }
        } else {
            status.value = ApiStatus.FAILURE
        }
        return getHighlight(categoryId)
    }


/*
    Hits the getHighlight endpoint,
    iterates through the content and retrieves the comma-separated ids and passes them to getItems
*/
    suspend fun getHighlight(categoryId: String): List<ItemResponse> {
        val response = api.getHighlights(categoryId)
        var idsHighlight: String = ""
        if (response.content.isNotEmpty()) {
            response.content.forEach {
                if (it.type == "ITEM") {
                    idsHighlight += "${it.id},"
                }
            }
            status.value = ApiStatus.SUCCESS
        } else {
            status.value = ApiStatus.FAILURE
        }
        return getItems(idsHighlight)
    }
/*
* returns the 20 most popular items on the platform
* */
    suspend fun getItems(ids: String): List<ItemResponse> {
        return api.getItems(ids)
    }


}