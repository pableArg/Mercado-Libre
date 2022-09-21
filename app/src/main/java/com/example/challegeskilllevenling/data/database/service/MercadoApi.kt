package com.example.challegeskilllevenling.data.database.service

import com.example.challegeskilllevenling.domain.category.CategoryItem
import com.example.challegeskilllevenling.domain.highlight.HighlightResponse
import com.example.challegeskilllevenling.domain.item.ItemResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoApi {

    companion object {
        const val SITE_ID = "MLA"
        const val TOKEN_MELI = "APP_USR-7659498699499354-092111-09610ca4932e755ce27e666ec7313d75-141318486"
    }

    @Headers("Authorization: Bearer $TOKEN_MELI")
    @GET("sites/$SITE_ID/domain_discovery/search")
    suspend fun getCategory(
        @Query("q") category: String,
        @Query("limit") limit: Int = 1
    ): List<CategoryItem>



    @Headers("Authorization: Bearer $TOKEN_MELI")
    @GET("highlights/MLA/category/{categoryId}")
    suspend fun getHighlights(@Path("categoryId") categoryList: String): HighlightResponse


    @Headers("Authorization: Bearer $TOKEN_MELI")
    @GET("items")
    suspend fun getItems(@Query("ids") itemsList: String): List<ItemResponse>


}