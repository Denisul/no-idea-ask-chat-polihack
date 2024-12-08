package com.ncorti.kotlin.template.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AnalysisFragment()
            1 -> RecommendationsFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}
