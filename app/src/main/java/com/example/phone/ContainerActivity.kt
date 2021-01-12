package com.example.phone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.phone.fragment.DrawableAnimFragment
import com.example.phone.fragment.LineChartFragment
import com.example.phone.fragment.ScrollSlideFragment

class ContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

}
