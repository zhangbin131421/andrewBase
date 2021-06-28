package com.andrew.library.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(
    fragmentManager
) {

    private val list = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    fun addAll(_list: List<Fragment>) {
        list.clear()
        list.addAll(_list)
        notifyDataSetChanged()
    }

    fun add(fragment: Fragment) {
        list.add(fragment)
    }
}
