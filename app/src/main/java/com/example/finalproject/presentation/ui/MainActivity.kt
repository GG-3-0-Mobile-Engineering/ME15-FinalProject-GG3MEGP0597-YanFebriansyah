package com.example.finalproject.presentation.ui

import TimePickerHelper
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.notification.NotificationFloodDepth
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.presentation.model.Bencana
import com.example.finalproject.presentation.ui.adapter.AdapterBencana
import com.example.finalproject.presentation.ui.adapter.AdapterFilter
import com.example.finalproject.presentation.viewmodel.HomeViewModel
import com.example.finalproject.utils.CheckConnection
import com.example.finalproject.utils.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback, AdapterFilter.FilterClickListener {

    private lateinit var adapterFilter: AdapterFilter
    private lateinit var adapterBencana: AdapterBencana
    private lateinit var binding: ActivityMainBinding
    private lateinit var mMap: GoogleMap
    var listOfBencana: MutableList<Bencana> = mutableListOf()
    var backupOfBencana: MutableList<Bencana> = mutableListOf()
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var autoCompleteAdapter: ArrayAdapter<String>

    private var mapReady = false
    private var mapLayoutReady = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check internet connection
        if (!CheckConnection.isInternetAvailable(this)) {
            CheckConnection.showInternetMessage(this)
            return
        }

        binding.btnTimePeriode.setOnClickListener {
            val timePickerHelper = TimePickerHelper(this)
            timePickerHelper.showTimePeriodPicker { selectedWeeks ->
                // Panggil fungsi untuk mengambil data dari service dengan time periode yang dipilih
                viewModel.getData(selectedWeeks.toString())
                observeData()
            }
        }


//      observeData
        observeData()


//        filter
        filterButton()

//        rv bencana
        listBencana()


//        bottomSheet
        bottomSheet()

//        Settings
        moveToSettings()

//        serachVIew
        searchViewAction()
    }

    private fun filterButton() {
        adapterFilter = AdapterFilter(this)
        binding.rvFilter.setHasFixedSize(true)
        binding.rvFilter.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFilter.adapter = adapterFilter
    }

    private fun listBencana() {
        adapterBencana = AdapterBencana(listOfBencana as ArrayList<Bencana>)
        binding.rvBencana.setHasFixedSize(true)
        binding.rvBencana.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvBencana.adapter = adapterBencana

        binding.appbar.rvBencana.setHasFixedSize(true)
        binding.appbar.rvBencana.layoutManager = LinearLayoutManager(applicationContext)
        binding.appbar.rvBencana.adapter = adapterBencana
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mapReady = true
        markerMap()
        // Tambahkan kode untuk memantau perubahan layout pada peta, code ini untuk solusi error ketika saya mencoba switch mode niht/light
        val mapView = findViewById<View>(R.id.map)
        mapView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Layout peta sudah terjadi
                mapLayoutReady = true
                markerMap() // Panggil markerMap() hanya jika layout sudah terjadi
                mapView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun showMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    private fun markerMap() {
        if (!mapReady || !mapLayoutReady) {
            // Peta belum siap atau layout belum terjadi, kembalikan saja
            return
        }
        mMap.clear()
        if (listOfBencana.isNotEmpty()) {
            for (location in listOfBencana) {
                val latitude = location.latitude
                val longitude = location.longitude

//                check if valid
                if (latitude != null && longitude != null) {
                    val locationLatLang = LatLng(latitude, longitude)
                    mMap.addMarker(MarkerOptions().position(locationLatLang).title(location.title))
                }
            }
            val builder = LatLngBounds.builder()
            for (location in listOfBencana) {
                val latitude = location.latitude
                val longitude = location.longitude

                // Check if latitude and longitude are valid
                if (latitude != null && longitude != null) {
                    val locationLatLng = LatLng(latitude, longitude)
                    builder.include(locationLatLng)
                }
            }
            val bounds = builder.build()
            val padding = 100 // Padding for the bounds (optional)
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mMap.animateCamera(cameraUpdate)
        }
    }

    private fun bottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 300
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.appbar.bottomAppbar.visibility = View.VISIBLE
                        binding.sheet.visibility = View.GONE
                    }

                    else -> {
                        binding.appbar.bottomAppbar.visibility = View.GONE
                        binding.sheet.visibility = View.VISIBLE

                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottomSheet.alpha = 1 - slideOffset
            }
        })
        binding.appbar.cancelBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun moveToSettings() {
        binding.btnSettings.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
//        cek kondisi bottomsheet
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.sheet)
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeData() {
        viewModel.getData.observe(this) { data ->
            if (data.isLoading) {
                binding.progressBar3.visibility = View.VISIBLE
            } else {
                binding.progressBar3.visibility = View.GONE
            }
            try {
                val dataBencana = data.disasterData
                if (dataBencana.isNotEmpty()) {
                    listOfBencana.clear()
                    listOfBencana.addAll(dataBencana)
                    backupOfBencana.addAll(dataBencana)
                    adapterBencana.notifyDataSetChanged()
                }
//              show map
                showMap()
                viewModel.checkNotification(this, backupOfBencana)
            } catch (e: Exception) {
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

//        set AutoComplete DropDown
        searchViewAction()
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onFilterClicked(type: String) {

        if (backupOfBencana.isNotEmpty()) {
            if (type == "all") {
                listOfBencana.clear()
                listOfBencana.addAll(backupOfBencana)
            } else {
                listOfBencana.clear()
                for (bencana in backupOfBencana) {
                    if (bencana.title == type) {
                        listOfBencana.add(bencana)
                    }
                }
            }
            adapterBencana.notifyDataSetChanged()
            markerMap()
        }
    }


    private fun searchViewAction() {
//        autoComplete
        val provinceName = Constants.provinceMap.keys.toList()
        val autoCompleteAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, provinceName)
        (binding.edtSearch as MaterialAutoCompleteTextView).setAdapter(autoCompleteAdapter)

        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(binding.edtSearch.text.toString())
                true
            } else {
                false
            }
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnClear.visibility =
                    if (s.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
                search(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                search(s.toString())
            }
        })
        binding.btnClear.setOnClickListener {
            binding.edtSearch.setText("")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun search(search: String) {
        lifecycleScope.launch {
            viewModel.searchData(search, backupOfBencana).collect { result ->
                listOfBencana.clear()
                listOfBencana.addAll(result)
                adapterBencana.notifyDataSetChanged()
                markerMap()
            }
        }


    }

    override fun onResume() {
        super.onResume()
        // Cek apakah mMap belum diinisialisasi dan peta sudah siap
        if (!::mMap.isInitialized && mapReady) {
            // Inisialisasi kembali mMap
            val mapFragment =
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }
}
