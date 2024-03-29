package com.example.sneakershop.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.sneakershop.adapter.BrandAdapter
import com.example.sneakershop.adapter.PopularAdapter
import com.example.sneakershop.adapter.SliderAdapter
import com.example.sneakershop.databinding.ActivityMainBinding
import com.example.sneakershop.model.SlideModel
import com.example.sneakershop.viewmodel.MainViewModel

class MainActivity : BaseActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initBrand()
        initItemPopular()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.cartButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CartActivity::class.java
                )
            )
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items ->
            banners(items)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images: List<SlideModel>) {
        binding.viewpaperSlider.adapter = SliderAdapter(images, binding.viewpaperSlider)
        binding.viewpaperSlider.clipToPadding = false
        binding.viewpaperSlider.clipChildren = false
        binding.viewpaperSlider.offscreenPageLimit = 3
        binding.viewpaperSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }

        binding.viewpaperSlider.setPageTransformer(compositePageTransformer)

        if (images.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewpaperSlider)
        }
    }

    private fun initBrand() {
        binding.progressBarBrand.visibility = View.VISIBLE
        viewModel.brands.observe(this, Observer {
            binding.viewBrand.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.viewBrand.adapter = BrandAdapter(it)
            binding.progressBarBrand.visibility = View.GONE
        })
        viewModel.loadBrand()
    }

    private fun initItemPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.itemPopular.observe(this, Observer {
            binding.viewPopular.layoutManager = GridLayoutManager(this, 2)
            binding.viewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()
    }
}