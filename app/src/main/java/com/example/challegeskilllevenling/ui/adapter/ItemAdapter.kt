package com.example.challegeskilllevenling.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challegeskilllevenling.databinding.ItemCardBinding
import com.example.challegeskilllevenling.domain.item.Item
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.activities.DetailActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ItemAdapter @Inject constructor(
    val context: Context,
    var itemList: List<ItemResponse>,
) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.onBind(item.body)

        holder.binding.conteiner.setOnClickListener {
            val obj = item
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("item_data", obj as Parcelable)
            context.startActivity(
                Intent.createChooser(intent, "Compartir en").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )


        }

    }

    override fun getItemCount(): Int = itemList.size
}

class ItemViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: Item) {
        Picasso.get().load(item.imagen).into(binding.imageView)
        binding.txtTitle.text = item.titulo
        binding.txtPrice.text = "$ ${item.precio}"


    }
}

