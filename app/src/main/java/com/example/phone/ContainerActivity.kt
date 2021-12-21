package com.example.phone

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.phone.fragment.DrawableAnimFragment
import com.example.phone.fragment.LineChartFragment
import com.example.phone.fragment.ScrollSlideFragment

class ContainerActivity : AppCompatActivity() {
    private val TAG = "ContainerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
        setContentView(R.layout.activity_container)

//        startScrollSlide()
//        startDrawableAnim()
        startLineChart()
    }

    private fun replaceFragment(fragment: Fragment, tag: String, addToBackStack: Boolean) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fl_container, fragment, tag)
        if (addToBackStack) {
            beginTransaction.addToBackStack(tag)
        }
        beginTransaction.commitAllowingStateLoss()
    }

    private fun startScrollSlide() {
        replaceFragment(ScrollSlideFragment.newInstance(), "FirstFragment", false)
    }

    private fun startDrawableAnim() {
        replaceFragment(DrawableAnimFragment.newInstance(), "DrawableAnimFragment", false)
    }

    private fun startLineChart() {
        replaceFragment(LineChartFragment.newInstance(), "LineChartFragment", false)
    }


    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
    }
}
