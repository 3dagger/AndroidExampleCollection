package com.dagger.mpandroidchart.ext

import android.app.Activity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dagger.mpandroidchart.base.BaseRecyclerView
import com.dagger.mpandroidchart.listener.RecyclerViewItemClickListener
import com.dagger.mpandroidchart.utility.RecyclerViewItemClick

@Suppress("UNCHECKED_CAST")
@BindingAdapter("addItems")
fun RecyclerView.addItems(list: List<Any>?) {
    (this.adapter as? BaseRecyclerView.Adapter<Any, *>)?.run {
        addItems(list)
        addActivity(context as Activity)
        notifyDataSetChanged()
    }
}

@BindingAdapter("itemClick")
fun RecyclerView.itemClick(listener: RecyclerViewItemClickListener) {
    addOnItemTouchListener(RecyclerViewItemClick(context, this, listener))
}




