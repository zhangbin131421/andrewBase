package com.andrew.library.adapter

import android.widget.BaseAdapter
import com.orhanobut.logger.Logger
import java.util.*

abstract class AndrewBaseAdapter<T> : BaseAdapter() {
    private val arrayList = ArrayList<T>()
    protected var clickPosition = -1
    override fun getItem(position: Int): T {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    fun add(t: T) {
        arrayList.add(t)
    }

    fun addNotify(t: T) {
        arrayList.add(t)
        notifyDataSetChanged()
    }

    /**
     * 
     */
    open fun addAll(list: List<T>?, clearOldData: Boolean) {
        list?.let {
            if (clearOldData) {
                arrayList.clear()
            }
            arrayList.addAll(it)
        }
    }

    open fun addAllNotify(list: List<T>?, clearOldData: Boolean) {
        list?.let {
            if (clearOldData) {
                arrayList.clear()
            }
            arrayList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun addAllHead(list: List<T>?): Boolean {
        return arrayList.addAll(0, list!!)
    }

    fun clear() {
        arrayList.clear()
    }

    fun deleteNotify() {
        if (clickPosition > -1 && clickPosition < count) {
            arrayList.removeAt(clickPosition)
            clickPosition = -1
            notifyDataSetChanged()
        } else {
            Logger.e("删除失败clickPosition=$clickPosition")
        }
    }
}