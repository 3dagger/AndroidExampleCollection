package com.dagger.infiniterecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dagger.infiniterecyclerview.databinding.ItemNoticeBinding
import com.dagger.infiniterecyclerview.databinding.ItemProgressBinding
import com.dagger.infiniterecyclerview.model.Content



class NoticeRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    companion object{
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    private val item = ArrayList<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_ITEM ->  NoticeViewHolder(binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> ProgressViewHolder(binding = ItemProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (item[position].title) {
            " " -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is NoticeViewHolder -> holder.bind(notice = item[position])
            is ProgressViewHolder -> holder.bind(position = position)
        }
    }

    override fun getItemCount(): Int = item.size

    fun setList(notice: MutableList<Content>) {
        item.addAll(notice)
        item.add(Content(" ", " "))
    }

    fun deleteProgress() {
        item.removeAt(item.lastIndex)
    }

    inner class NoticeViewHolder(private val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: Content) {
            binding.data = notice
        }
    }

    inner class ProgressViewHolder(private val binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

        }
    }
}