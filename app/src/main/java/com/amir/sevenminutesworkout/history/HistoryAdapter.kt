package com.amir.sevenminutesworkout.history

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amir.sevenminutesworkout.R
import com.amir.sevenminutesworkout.databinding.ItemHistoryRowBinding
import com.amir.sevenminutesworkout.room.HistoryEntity

class HistoryAdapter(private val dates: ArrayList<HistoryEntity>) :
    RecyclerView.Adapter<HistoryAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder(
            ItemHistoryRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
       //val context = holder.itemView.context
        val date = dates[position]
        val rowNum = position+1
        holder.tvPosition.text = rowNum.toString()
        holder.tvDate.text = date.date
        if (position % 2 == 0) {
            holder.ll_history_item_main.setBackgroundColor( ContextCompat.getColor( holder.itemView.context, R.color.lightGray ) )
        } else {
            holder.ll_history_item_main.setBackgroundColor( ContextCompat.getColor( holder.itemView.context, R.color.white ) )
           // holder.ll_history_item_main.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }


    }

    override fun getItemCount(): Int {
        return dates.size
    }

    class MViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val ll_history_item_main = binding.llHistoryItemMain
        val tvPosition = binding.tvPosition
        val tvDate = binding.tvDate

    }


}