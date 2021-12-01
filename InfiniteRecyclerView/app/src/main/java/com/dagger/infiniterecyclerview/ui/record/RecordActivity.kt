package com.dagger.infiniterecyclerview.ui.record

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dagger.infiniterecyclerview.R
import com.dagger.infiniterecyclerview.adapter.RecordRecyclerViewAdapter
import com.dagger.infiniterecyclerview.base.BaseActivity
import com.dagger.infiniterecyclerview.databinding.ActivityRecordBinding
import com.dagger.infiniterecyclerview.ui.record.model.RecordViewModel
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

class RecordActivity : BaseActivity<ActivityRecordBinding, RecordViewModel>(), RecordNavigator.View {
    override val viewModel: RecordViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_record

    private lateinit var recordAdapter : RecordRecyclerViewAdapter
    private var page = 1

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@RecordActivity)
    }

    override fun onProcess() {
        initViewSetup()
        subscribeObservers()
    }

    private fun initViewSetup() {
        viewDataBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecordActivity, LinearLayoutManager.VERTICAL, false)
            recordAdapter = RecordRecyclerViewAdapter()
            adapter?.setHasStableIds(true)
            adapter = recordAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    if(!viewDataBinding.recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            recordAdapter.deleteProgressItem()
                            viewModel.onLoadRecordData(page = ++page)
                        }, 500)

                    }
                }
            })
        }
    }

    private fun subscribeObservers() {
        viewModel.recordData.observe(this@RecordActivity, Observer {
            recordAdapter.addItem(data = viewModel.prepareRecordAdapter(it))
            recordAdapter.notifyDataSetChanged()
//            recordAdapter.notifyItemRangeInserted((page - 1) * 10, 10)
        })
    }


    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}