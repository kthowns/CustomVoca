package com.example.customvoca.dto

class LoginDto{
    data class Request(
        var username: String,
        var password: String
    )
    data class Response(
        var userId: Int,
        var username: String
    )
}

