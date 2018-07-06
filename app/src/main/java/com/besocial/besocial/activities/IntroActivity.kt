package com.besocial.besocial.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.besocial.besocial.R
import com.besocial.besocial.adapters.ViewPagerAdapter
import com.besocial.besocial.fragments.IntroFragment
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    private val viewPagerChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            // TODO
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            // TODO
        }

        override fun onPageSelected(position: Int) {
            when (position) {
                0 -> window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorYellow)
                1 -> window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorOrange)
                2 -> window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorTeal)
                3 -> window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        setupViewPager(vp_intro)
        vp_intro.addOnPageChangeListener(viewPagerChangeListener)
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(IntroFragment.newInstance(0), "")
        adapter.addFragment(IntroFragment.newInstance(1), "")
        adapter.addFragment(IntroFragment.newInstance(2), "")
        adapter.addFragment(IntroFragment.newInstance(4), "")

        viewPager.adapter = adapter
    }
}
