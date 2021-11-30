package com.dagger.infiniterecyclerview.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dagger.infiniterecyclerview.databinding.ItemProgressBinding
import com.dagger.infiniterecyclerview.databinding.ItemRecordContentsBinding
import com.dagger.infiniterecyclerview.databinding.ItemRecordHeaderBinding
import com.dagger.infiniterecyclerview.model.DummyData
import com.dagger.infiniterecyclerview.model.History
import com.dagger.infiniterecyclerview.ui.record.model.FamilyModel

class RecordRecyclerViewAdapter(var context: Context, var list: MutableList<FamilyModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_CONTENTS = 1
        const val VIEW_TYPE_PROGRESS = 2
    }

    private val item = ArrayList<History>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = item.size

    fun setItems(data: MutableList<History>) {
        item.addAll(data)
    }


    inner class HeaderViewHolder(private val binding: ItemRecordHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: History) {
            binding.dummy = data
        }
    }

    inner class ContentsViewHolder(private val binding: ItemRecordContentsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: History) {
            binding.dummy = data
        }
    }

    inner class ProgressViewHolder(private val binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }


}