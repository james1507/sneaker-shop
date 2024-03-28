package com.example.sneakershop.viewmodel

import android.content.ClipData.Item
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sneakershop.model.BrandModel
import com.example.sneakershop.model.ItemModel
import com.example.sneakershop.model.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel() : ViewModel() {
    private val firebaseDatabase =
        FirebaseDatabase.getInstance("https://sneakershop-992b1-default-rtdb.asia-southeast1.firebasedatabase.app")

    private val _banner = MutableLiveData<List<SlideModel>>()
    private val _brand = MutableLiveData<MutableList<BrandModel>>()
    private val _itemPopular = MutableLiveData<MutableList<ItemModel>>()

    val banners: LiveData<List<SlideModel>> = _banner
    val brands: LiveData<MutableList<BrandModel>> = _brand
    val itemPopular: LiveData<MutableList<ItemModel>> = _itemPopular

    fun loadBanners() {
        val ref = firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SlideModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SlideModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadBrand() {
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<BrandModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(BrandModel::class.java)
                    if(list!=null) {
                        lists.add(list)
                    }
                }
                _brand.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadPopular() {
        val ref = firebaseDatabase.getReference("Items")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemModel::class.java)
                    if(list!=null) {
                        lists.add(list)
                    }
                }
                _itemPopular.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}