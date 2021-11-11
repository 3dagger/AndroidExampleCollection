package com.trycatch.recyclerviewswipemenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.trycatch.recyclerviewswipemenu.databinding.ItemSwipeBinding

class SwipeListAdapter(var listener: RecyclerViewOnClickListener) : RecyclerView.Adapter<SwipeListAdapter.SwipeViewHolder>() {


    private val items: MutableList<String> = mutableListOf<String>().apply {
        for (i in 0..20)
            add("$i")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeViewHolder = SwipeViewHolder(
        ItemSwipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SwipeViewHolder, position: Int) {
        Logger.d("aa :: position :: $position")
        holder.bind(items[position])

    }

    override fun getItemCount(): Int = items.size

    inner class SwipeViewHolder(private val binding: ItemSwipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(label: String) {
            binding.label = label
            binding.task.setOnClickListener {
                Snackbar.make(it, "$label click", Snackbar.LENGTH_SHORT).show()
                Logger.d("task click")
                listener.onLongClick(position)
                binding.swipeView.translationX = 0f
            }
//            binding.swipeView.setOnClickListener {
//                Logger.d("swipeView Click")
//            }
            binding.swipeView.setOnLongClickListener {
//                for(i in items.indices) {
//                    items[i].
//                }
                    it.translationX = -200f

//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    listener.onLongClick(position)
//                }
                true
            }
        }
    }
}