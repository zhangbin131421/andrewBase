package com.andrew.library.listener

interface MyOnClickPositionListener<T> {
    fun onClick(t: T, position: Int)
}