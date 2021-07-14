package com.swvl.assessmenttest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.swvl.assessmenttest.R
import com.swvl.assessmenttest.databinding.ActivitySplashBinding
import com.swvl.assessmenttest.utils.finishAndNavigateTo
import com.swvl.assessmenttest.viewmodel.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private val binding: ActivitySplashBinding by viewBinding()

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)
        viewModel.showLoading.observe(this ) {
            if (it) binding.progressSplash.visibility = View.VISIBLE else binding.progressSplash.visibility = View.GONE

        }

        viewModel.moviesListData.observe(this ) {
            finishAndNavigateTo(MainActivity::class.java)
        }
    }
}