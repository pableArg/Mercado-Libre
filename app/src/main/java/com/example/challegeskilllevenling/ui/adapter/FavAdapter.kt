package com.example.challegeskilllevenling.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challegeskilllevenling.databinding.ItemCardBinding
import com.example.challegeskilllevenling.domain.item.Item
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.squareup.picasso.Picasso
import javax.inject.Inject

class FavAdapter @Inject constructor(
    var itemList: List<ItemResponse>

): RecyclerView.Adapter<FavViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val itemBinding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val item = itemList[position]
        holder.onBind(item.body)
    }

    override fun getItemCount(): Int = itemList.size


}
class FavViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
/**
 * For each item binding the xml with the item information
  */
    @SuppressLint("SetTextI18n")
    fun onBind(item: Item) {
        Picasso.get().load(item.imagen).into(binding.imageView)
        binding.txtTitle.text = item.titulo
        binding.txtPrice.text = "$ ${item.precio}"


    }
}