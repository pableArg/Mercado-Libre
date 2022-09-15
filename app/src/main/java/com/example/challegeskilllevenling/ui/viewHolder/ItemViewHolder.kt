package com.example.challegeskilllevenling.ui.viewHolder

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.challegeskilllevenling.databinding.ItemCardBinding
import com.example.challegeskilllevenling.domain.item.Item
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.DetailActivity
import com.squareup.picasso.Picasso
import java.io.Serializable
import javax.inject.Inject
/*
class ItemViewHolder @Inject constructor(view: View) : RecyclerView.ViewHolder(view)  {

    private val binding = ItemCardBinding.bind(view)

    fun onBind (item: Item,onClickListener: (Item)->Unit){
        Picasso.get().load(item.imagen).into(binding.imageView)
        binding.txtTitle.text = item.titulo
        binding.txtPrice.text = "$ ${item.precio}"



        }

    }

*/