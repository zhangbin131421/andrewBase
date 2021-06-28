package com.andrew.library.net


class ApiResponse<T>(var data: T?, var errorCode: Int, var errorMsg: String)