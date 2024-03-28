package com.example.sneakershop.activity

import android.content.Intent
import android.os.Bundle
import com.example.sneakershop.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {
    private lateinit var bidding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bidding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(bidding.root)

        bidding.startButton.setOnClickListener() {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}