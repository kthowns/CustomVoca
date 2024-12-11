package com.example.customvoca.dto

data class ApiResponse<T>(
    var status: Int,
    var message: String?,
    var data: T?
)

data class ApiError(
    var status: Int,
    var message: String
)