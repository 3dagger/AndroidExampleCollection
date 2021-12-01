package com.dagger.infiniterecyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dagger.infiniterecyclerview.databinding.ItemProgressBinding
import com.dagger.infiniterecyclerview.databinding.ItemRecordContentsBinding
import com.dagger.infiniterecyclerview.databinding.ItemRecordHeaderBinding
import com.dagger.infiniterecyclerview.model.DummyData
import com.dagger.infiniterecyclerview.model.History
import com.dagger.infiniterecyclerview.model.RentList
import com.dagger.infiniterecyclerview.ui.record.model.FamilyModel

//class RecordRecyclerViewAdapter(var context: Context, var list: MutableList<FamilyModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
class RecordRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val list = ArrayList<FamilyModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return when(viewType) {
            FamilyModel.PARENT -> HeaderViewHolder(parent)
            FamilyModel.CHILD -> ContentsViewHolder(parent)
            FamilyModel.PROGRESS -> ProgressViewHolder(parent)
            else -> ContentsViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HeaderViewHolder -> holder.bind(list[position].parent)
            is ContentsViewHolder -> holder.bind(list[position].child)
            is ProgressViewHolder -> holder
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].type

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    fun addItem(data: MutableList<FamilyModel>) {
        list.addAll(data)
    }

    fun deleteProgressItem() {
        list.removeAt(list.lastIndex)
    }


    inner class HeaderViewHolder(private val binding: ItemRecordHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(ItemRecordHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        fun bind(data: History) {
            binding.dummy = data
        }
    }

    inner class ContentsViewHolder(private val binding: ItemRecordContentsBinding) : RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(ItemRecordContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        fun bind(data: RentList) {
            binding.dummy = data
        }
    }

    inner class ProgressViewHolder(private val binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(ItemProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        fun bind() {

        }
    }


}