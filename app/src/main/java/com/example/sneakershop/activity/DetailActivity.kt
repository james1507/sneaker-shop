package com.example.sneakershop.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakershop.adapter.ColorAdapter
import com.example.sneakershop.adapter.SizeAdapter
import com.example.sneakershop.adapter.SliderAdapter
import com.example.sneakershop.databinding.ActivityDetailBinding
import com.example.sneakershop.helper.ManagementCart
import com.example.sneakershop.model.ItemModel
import com.example.sneakershop.model.SlideModel

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemModel
    private var numberOder = 1
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)
        getBundle()
        banners()
        initLists()
    }

    private fun initLists() {
        val sizeList = ArrayList<String>()
        for (size in item.size) {
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter = SizeAdapter(sizeList)
        binding.sizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }

        binding.colorList.adapter = ColorAdapter(colorList)
        binding.colorList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val sliderItems = ArrayList<SlideModel>()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SlideModel(imageUrl))
        }
        binding.slider.adapter = SliderAdapter(sliderItems, binding.slider)
        binding.slider.clipToPadding = false
        binding.slider.clipChildren = false
        binding.slider.offscreenPageLimit = 3
        binding.slider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        if (sliderItems.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.slider)
        }
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "$" + item.price
        binding.ratingTxt.text = "${item.rating} Rating"
        binding.addToCartButton.setOnClickListener {
            item.numberInCart = numberOder
            managementCart.insertFood(item)
        }
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.cartButton.setOnClickListener {

        }
    }
}