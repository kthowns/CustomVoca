package com.example.customvoca.dto

import java.sql.Timestamp

data class WordDto (
    var wordId: Int,
    var vocabId: Int,
    var expression: Int,
    var createdAt: Timestamp
)

class CreateWord {
    data class Request(
        var expression: String
    )

    data class Response(
        var wordId: Int,
        var vocabId: Int,
        var expression: Int,
        var createdAt: Timestamp
    )
}