package com.andrew.library.adapter

import android.content.Context
import androidx.databinding.library.baseAdapters.BR

abstract class BaseRecyclerViewAdapter<T>(context: Context) :
    AndrewRecyclerViewAdapter<T>(context) {

    public override fun mOnBindViewHolder(holder: BaseRecyclerViewHolder, position: Int, t: T) {
        holder.binding.setVariable(BR._all, t)
        holder.binding.executePendingBindings()
    }
}