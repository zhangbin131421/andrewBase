package com.example.kotlin.mode

data class Page<T>(var page: Int, var pageSize: Int, var rows: List<T>, var totalPage: Int, var totalRows: Int) {
}