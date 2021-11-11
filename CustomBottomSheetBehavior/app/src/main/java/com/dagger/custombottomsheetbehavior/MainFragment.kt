package com.dagger.custombottomsheetbehavior

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dagger.custombottomsheetbehavior.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.orhanobut.logger.Logger


class MainFragment : Fragment(), OnMapReadyCallback{
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var gpsTracker : GpsTracker? = null
    private lateinit var locationSource                 : FusedLocationSource
    private lateinit var nMap                           : NaverMap

    lateinit var persistentBottomSheetBehavior  : BottomSheetBehavior<FrameLayout>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        gpsTracker = GpsTracker(context = requireActivity())
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val mapFragment = childFragmentManager.findFragmentById(R.id.naverMapFragment) as MapFragment?
            ?: MapFragment.newInstance().also { childFragmentManager.beginTransaction().add(R.id.naverMapFragment, it).commit() }

        mapFragment?.getMapAsync(this)
        locationSource = FusedLocationSource(this, 100)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initPersistentBottomSheetBehavior() {
        persistentBottomSheetBehavior = BottomSheetBehavior.from(binding.sampleBottomSheet).apply {
            isHideable = true
            state = BottomSheetBehavior.STATE_HIDDEN
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val bottomSheetVisibleHeight = bottomSheet.height - bottomSheet.top

                    requireActivity().findViewById<TextView>(R.id.pinned_center).translationY =
                        (bottomSheetVisibleHeight - requireActivity().findViewById<TextView>(R.id.pinned_center).height) / 2f

                    requireActivity().findViewById<TextView>(R.id.pinned_bottom).translationY =
                        (bottomSheetVisibleHeight - requireActivity().findViewById<TextView>(R.id.pinned_bottom).height).toFloat()
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }
            })
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        initPersistentBottomSheetBehavior()

        nMap = naverMap
        nMap.locationSource = locationSource

        nMap.apply {
            cameraPosition = CameraPosition(LatLng(gpsTracker!!.getLatitude(), gpsTracker!!.getLongitude()), 14.0, 0.0, 0.0)
            uiSettings.apply {
                isLocationButtonEnabled = true
                isCompassEnabled = false
                isZoomControlEnabled = true
                isTiltGesturesEnabled = false
                isRotateGesturesEnabled = true
                isScaleBarEnabled = false
                setLogoMargin(20, 100, 200, 20)
            }
            mapType = NaverMap.MapType.Navi
            locationTrackingMode = LocationTrackingMode.Follow
        }

        Marker().apply {
            position = LatLng(gpsTracker!!.getLatitude(), gpsTracker!!.getLongitude())
            map = nMap
            width = Marker.SIZE_AUTO
            height = Marker.SIZE_AUTO

            setOnClickListener {
                Logger.d("click")
                persistentBottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                true
            }
        }
    }
}