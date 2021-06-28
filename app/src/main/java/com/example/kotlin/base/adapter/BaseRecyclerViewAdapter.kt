package com.example.kotlin.base.adapter

import android.content.Context
import com.andrew.library.adapter.AndrewRecyclerViewAdapter
import com.andrew.library.adapter.BaseRecyclerViewHolder
import androidx.databinding.library.baseAdapters.BR


abstract class BaseRecyclerViewAdapter<T>(context: Context) : AndrewRecyclerViewAdapter<T>(context) {

    public override fun mOnBindViewHolder(holder: BaseRecyclerViewHolder, position: Int, t: T) {
        holder.binding.setVariable(BR.item, t)
        holder.binding.executePendingBindings()
    }
}