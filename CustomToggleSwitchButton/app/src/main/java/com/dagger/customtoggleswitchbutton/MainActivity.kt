package com.dagger.customtoggleswitchbutton

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dagger.customtoggleswitchbutton.databinding.ActivityMainBinding
import androidx.core.content.ContextCompat
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var isLinearToggle = false
    var isConstraintToggle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.xmlLinear.setOnClickListener {
            isLinearToggle = !isLinearToggle
           setXMLToggle(isLinearToggle)
        }

        binding.xmlConstraint.setOnClickListener {
            isConstraintToggle = !isConstraintToggle
            if(isConstraintToggle) {
                binding.backgroundView.animate().withEndAction {
                    Log.d("leeam", "end finish")
                }.setDuration(200).translationX(160F).start()
                binding.viewTv2.setTextColor(Color.parseColor("#909090"))
                binding.viewTv2.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrmedium)
                binding.subScriberTv2.setTextColor(Color.parseColor("#4b4b4b"))
                binding.subScriberTv2.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrbold)
            }else {
                binding.backgroundView.animate().setDuration(200).translationX(0F).start()
                binding.viewTv2.setTextColor(Color.parseColor("#4b4b4b"))
                binding.viewTv2.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrbold)
                binding.subScriberTv2.setTextColor(Color.parseColor("#909090"))
                binding.subScriberTv2.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrmedium)
            }
        }
    }

    private fun setXMLToggle(isViewClicked: Boolean) {
        if (isViewClicked) {
            binding.viewTv.setTextColor(Color.parseColor("#909090"))
            binding.viewTv.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrmedium)
            binding.viewTv.setBackgroundResource(0)

            binding.subScriberTv.setTextColor(Color.parseColor("#4b4b4b"))
            binding.subScriberTv.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrbold)
            binding.subScriberTv.setBackgroundResource(R.drawable.item_bg_on)
        } else {
            binding.viewTv.setTextColor(Color.parseColor("#4b4b4b"))
            binding.viewTv.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrbold)
            binding.viewTv.setBackgroundResource(R.drawable.item_bg_on)
            binding.subScriberTv.setTextColor(Color.parseColor("#909090"))
            binding.subScriberTv.typeface = ResourcesCompat.getFont(this@MainActivity, R.font.notosanskrmedium)
            binding.subScriberTv.setBackgroundResource(0)
        }
    }
}