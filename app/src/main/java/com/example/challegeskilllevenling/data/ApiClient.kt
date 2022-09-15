package com.example.challegeskilllevenling.data

import com.example.challegeskilllevenling.domain.category.CategoryItem
import com.example.challegeskilllevenling.domain.highlight.HighlightResponse
import com.example.challegeskilllevenling.domain.item.Item
import com.example.challegeskilllevenling.domain.item.ItemResponse
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val api: MercadoApi
) {
    
    suspend fun getCategories(q: String): Response<List<CategoryItem>> {
        return api.getCategory(q)
    }

    suspend fun getHighlight(categoryId: String): Response<HighlightResponse> {
        return api.getHighlights(categoryId)

    }

    suspend fun getItems(ids: String): Response<List<ItemResponse>> {
        return api.getItems(ids)
    }


}