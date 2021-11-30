package com.dagger.infiniterecyclerview.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dagger.infiniterecyclerview.R
import com.dagger.infiniterecyclerview.adapter.NoticeRecyclerViewAdapter
import com.dagger.infiniterecyclerview.base.BaseActivity
import com.dagger.infiniterecyclerview.databinding.ActivityMainBinding
import com.dagger.infiniterecyclerview.ui.main.model.MainViewModel
import com.dagger.infiniterecyclerview.ui.record.RecordActivity
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View {
    override val viewModel: MainViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_main

    private lateinit var noticeAdapter: NoticeRecyclerViewAdapter
    var page = 1

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setNavigator(this@MainActivity)

        viewDataBinding.run {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            vm = viewModel
        }
    }

    override fun onProcess() {
        initViewSetup()
        subscribeObservers()
    }

    fun moveRecordActivity() {
        startActivity(Intent(this@MainActivity, RecordActivity::class.java))
    }

    /**
     * @author : 이수현
     * @Date : 2021/11/30 12:28 오후
     * @Description :
     * @History :
     *
     **/
    private fun initViewSetup() {
        viewDataBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)!!.findLastVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                if(!viewDataBinding.recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    noticeAdapter.deleteProgress()
                    viewModel.onLoadNoticeData(page = ++page)
                }
            }
        })

        viewDataBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            noticeAdapter = NoticeRecyclerViewAdapter()
            adapter = noticeAdapter
        }
    }

    /**
     * @author : 이수현
     * @Date : 2021/11/30 12:28 오후
     * @Description :
     * @History :
     *
     **/
    private fun subscribeObservers() {
        viewModel.noticeData.observe(this@MainActivity, Observer {
            noticeAdapter.setList(it.data.content?: mutableListOf())
            noticeAdapter.notifyItemRangeInserted((page - 1) * 10, 10)
        })
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}