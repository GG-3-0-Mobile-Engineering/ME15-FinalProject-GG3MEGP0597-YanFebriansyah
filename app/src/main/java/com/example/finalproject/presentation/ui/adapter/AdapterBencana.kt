package com.example.finalproject.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemBencanaBinding
import com.example.finalproject.presentation.model.Bencana
import com.squareup.picasso.Picasso

class AdapterBencana(
    val list: ArrayList<Bencana>
) : RecyclerView.Adapter<AdapterBencana.ViewHolder>() {

    class ViewHolder(private val binding: ItemBencanaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgBecana = binding.imgBencana
        val titleBencana = binding.titleBencana
        val descriptionBencana = binding.tvDescriptionBecana
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterBencana.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemBencanaBinding = ItemBencanaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterBencana.ViewHolder, position: Int) {
        val bencana = list[position]

        if (bencana.image.isNullOrEmpty()) {
            holder.imgBecana.setImageResource(R.drawable.no_image)
        } else {
            Picasso.get().load(bencana.image).into(holder.imgBecana)
        }

        holder.titleBencana.text = bencana.title
        holder.descriptionBencana.text = bencana.description
    }

    override fun getItemCount(): Int {
        return list.size
    }
}