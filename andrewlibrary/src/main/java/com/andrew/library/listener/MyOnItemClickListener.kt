package com.andrew.library.listener

interface MyOnItemClickListener<T> {
    fun onClick(list: List<T>?, position: Int)
}