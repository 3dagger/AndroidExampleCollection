package com.dagger.daggerhiltnetworkconnection.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import com.dagger.daggerhiltnetworkconnection.Constants.Companion.INTENT_ARGUMENT_USER_ID
import com.dagger.daggerhiltnetworkconnection.R
import com.dagger.daggerhiltnetworkconnection.base.BaseActivity
import com.dagger.daggerhiltnetworkconnection.databinding.ActivityMainBinding
import com.dagger.daggerhiltnetworkconnection.extensions.openActivity
import com.dagger.daggerhiltnetworkconnection.presentation.detail.DetailActivity
import com.dagger.daggerhiltnetworkconnection.utils.Resource.Status.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()


    override fun initView(savedInstanceState: Bundle?) {
        binding {
            activity = this@MainActivity
            vm = viewModel
        }

        subscribeObservers()
    }

    override fun onProcess() {
//        binding.btnFindUser.setOnClickListener {
//            viewModel.getUserInfo(userId = binding.edtInputUserId.text.toString())
//        }
    }

    fun moveDetail() {
        openActivity(DetailActivity::class.java) {
            putString(INTENT_ARGUMENT_USER_ID, binding.edtInputUserId.text.toString())
        }
    }

    private fun subscribeObservers() {

    }

}
