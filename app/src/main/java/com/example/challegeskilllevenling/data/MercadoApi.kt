package com.example.challegeskilllevenling.data

import com.example.challegeskilllevenling.domain.category.CategoryItem
import com.example.challegeskilllevenling.domain.category.CategoryResponse
import com.example.challegeskilllevenling.domain.highlight.HighlightResponse
import com.example.challegeskilllevenling.domain.item.Item
import com.example.challegeskilllevenling.domain.item.ItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoApi {

    companion object {
        const val SITE_ID = "MLA"
        const val TOKEN_MELI = "APP_USR-7659498699499354-091513-7f1f3f60e4b664477172b73fb70a73e3-141318486"
    }

    @Headers("Authorization: Bearer $TOKEN_MELI")
    @GET("sites/$SITE_ID/domain_discovery/search")
    suspend fun getCategory(
        @Query("q") category: String,
        @Query("limit") limit: Int = 1
    ): Response<List<CategoryItem>>



    @Headers("Authorization: Bearer $TOKEN_MELI")
    @GET("highlights/MLA/category/{categoryId}")
    suspend fun getHighlights(@Path("categoryId") categoryList: String): Response<HighlightResponse>


    @Headers("Authorization: Bearer $TOKEN_MELI")
    @GET("items")
    suspend fun getItems(@Query("ids") itemsList: String): Response<List<ItemResponse>>


}