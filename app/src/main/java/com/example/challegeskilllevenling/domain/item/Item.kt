package com.example.challegeskilllevenling.domain.item

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item (
    var id : String,
    @SerializedName("title")
    var titulo : String,
    @SerializedName("price")
    var precio : Double,
    @SerializedName("thumbnail")
    var imagen : String

        ) : Parcelable