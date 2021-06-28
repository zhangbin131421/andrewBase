package com.andrew.library.adapter

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter


class ViewPagerAdapter : PagerAdapter() {

    private val list = ArrayList<View>()
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = list[position]
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(list[position])
    }

    override fun getItemPosition(`object`: Any): Int {// 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE
    }
    fun addAll(_list: List<View>) {
        list.clear()
        list.addAll(_list)
        notifyDataSetChanged()
    }

    fun add(view: View) {
        list.add(view)
    }

}
