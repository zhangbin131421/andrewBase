package com.andrew.library.listener

interface MyOnLongClickListener<T> {
    fun onLongClick(t: T, position: Int)
}