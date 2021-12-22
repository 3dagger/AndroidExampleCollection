package com.dagger.daggerhiltnetworkconnection.ui.main

import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.dagger.daggerhiltnetworkconnection.R
import com.dagger.daggerhiltnetworkconnection.databinding.ActivityMainBinding
import com.google.android.material.transition.MaterialContainerTransformSharedElementCallback
import com.skydoves.bindables.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
//@AndroidEntryPoint
//class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
//
//    @get:VisibleForTesting
//    internal val viewModel: MainViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
//        window.sharedElementsUseOverlay = false
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_main)
//        binding {
//
//        }
//    }
//}