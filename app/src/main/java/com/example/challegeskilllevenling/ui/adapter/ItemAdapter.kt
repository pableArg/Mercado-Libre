package com.example.challegeskilllevenling.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challegeskilllevenling.databinding.ItemCardBinding
import com.example.challegeskilllevenling.domain.item.Item
import com.example.challegeskilllevenling.domain.item.ItemResponse
import com.example.challegeskilllevenling.ui.DetailActivity

import java.io.Serializable
import javax.inject.Inject

class ItemAdapter @Inject constructor(
     val context : Context,
        var itemList: List<ItemResponse>,
        private val onClickListener: (Item)->Unit
) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
       // holder.onBind(item.body, onClickListener)

      holder.binding.conteiner.setOnClickListener {
          val obj = item
          val intent = Intent (context, DetailActivity::class.java)
          intent.putExtra("ITEM", obj as Serializable)
          context.startActivity(intent)


      }

    }

    override fun getItemCount(): Int = itemList.size
}
class ItemViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

