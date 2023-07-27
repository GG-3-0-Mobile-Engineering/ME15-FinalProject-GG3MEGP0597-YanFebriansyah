package com.example.finalproject.presentation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R

class AdapterFilter(
    private val onFilterListener: FilterClickListener
) : RecyclerView.Adapter<AdapterFilter.ViewHolder>() {

    private var selectedPosition = 0

    val listFilter = listOf<String>(
        "all", "flood", "wind", "haze", "earthquake", "fire", "volcano"
    )


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val icFilter: ImageView = item.findViewById(R.id.img_ic_add)
        val layout: ConstraintLayout = item.findViewById(R.id.constraint_filter)
        val textBencana: TextView = item.findViewById(R.id.tv_ket_filter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFilter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_filter_disaster, parent, false
        )
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: AdapterFilter.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val filter = listFilter[position]

        holder.textBencana.text = filter



//    set warna clickt  mengikuti theme
        if (position == selectedPosition) {
            holder.textBencana.setTextColor(Color.WHITE)
            holder.icFilter.setImageResource(R.drawable.baseline_add_24_white)
            holder.layout.setBackgroundResource(R.drawable.shape_botton_filter_green)
        }else{
            val context = holder.itemView.context
            val theme = context.theme
            val typedValue = TypedValue()
            theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true)
            val colorOnPrimary = typedValue.data
            holder.textBencana.setTextColor(colorOnPrimary)
            holder.icFilter.setImageResource(R.drawable.baseline_add_black)
            holder.layout.setBackgroundResource(R.drawable.shape_botton_filter_white)
        }

        holder.itemView.setOnClickListener {
            if (selectedPosition != position) {
                selectedPosition = position

                onFilterListener.onFilterClicked(filter)
                notifyDataSetChanged()
            }
        }
    }


    override fun getItemCount(): Int {
        return listFilter.size
    }

    interface FilterClickListener {
        fun onFilterClicked(type: String)
    }

}
