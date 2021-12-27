package com.dagger.daggerhiltnetworkconnection.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.dagger.daggerhiltnetworkconnection.Constants.Companion.INTENT_ARGUMENT_USER_ID
import com.dagger.daggerhiltnetworkconnection.R
import com.dagger.daggerhiltnetworkconnection.base.BaseActivity
import com.dagger.daggerhiltnetworkconnection.databinding.ActivityDetailBinding
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val viewModel: DetailViewModel by viewModels()

    private var argumentUserId: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        binding {
            activity = this@DetailActivity
            vm = viewModel
        }

        if(intent != null) argumentUserId = intent.getStringExtra(INTENT_ARGUMENT_USER_ID)

        Logger.d("res :: ${intent.getStringExtra(INTENT_ARGUMENT_USER_ID)}")
    }

    override fun onProcess() {
        viewModel.getUserInfo(userId = argumentUserId)

//        viewModel.userInfo.observe(this@DetailActivity, Observer {
//            binding.webView.loadUrl(it.data?.htmlUrl.toString())
//        })
    }
}