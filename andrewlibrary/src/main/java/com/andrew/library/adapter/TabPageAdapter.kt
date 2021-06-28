package com.andrew.library.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabPageAdapter(fm: FragmentManager, private val fragments: List<Fragment>, private val tabNames: Array<String>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) { //FragmentStatePagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT, 兼容以前那种方式实现的懒加载
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabNames[position]
    }
}