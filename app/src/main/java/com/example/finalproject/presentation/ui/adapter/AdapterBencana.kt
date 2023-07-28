package com.example.finalproject.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemBencanaBinding
import com.example.finalproject.presentation.model.Bencana
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class AdapterBencana(
    val list: ArrayList<Bencana>
) : RecyclerView.Adapter<AdapterBencana.ViewHolder>() {


    class ViewHolder(private val binding: ItemBencanaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgBecana = binding.imgBencana
        val titleBencana = binding.titleBencana
        val descriptionBencana = binding.tvDescriptionBecana
        val time = binding.tvTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterBencana.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemBencanaBinding = ItemBencanaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterBencana.ViewHolder, position: Int) {
        val bencana = list[position]


        val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val targetDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val date = apiDateFormat.parse(bencana.time)
        val formattedDate = targetDateFormat.format(date)

        if (bencana.image.isNullOrEmpty()) {
            holder.imgBecana.setImageResource(R.drawable.no_image)
        } else {
            Picasso.get().load(bencana.image).into(holder.imgBecana)
        }

        holder.titleBencana.text = bencana.title
        holder.time.text = formattedDate
        holder.descriptionBencana.text = bencana.description
    }

    override fun getItemCount(): Int {
        return list.size
    }
}