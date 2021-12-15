package com.dagger.mpandroidchart.listener

interface RecyclerViewItemClickListener {
    fun onRecyclerItemClick(position : Int)

    fun onRecyclerLongItemClick(position : Int)
}