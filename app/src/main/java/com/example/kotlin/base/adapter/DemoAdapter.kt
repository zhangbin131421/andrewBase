package com.example.kotlin.base.adapter

import android.content.Context
import com.example.kotlin.R
import com.example.kotlin.mode.DemoData

class DemoAdapter(context: Context) : BaseRecyclerViewAdapter<DemoData>(context) {
    override val layoutId: Int = R.layout.item_demo


}