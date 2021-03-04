package com.silva021.myapplication.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.silva021.myapplication.R
import com.silva021.myapplication.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.title = "Sobre"
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mBinding.btnGitHub.setOnClickListener {
            openURL("https://github.com/silva021")
        }
        mBinding.btnLinkedin.setOnClickListener {
            openURL("https://www.linkedin.com/in/silva021/")
        }
    }

    private fun openURL(URL: String) = startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(URL)))

}