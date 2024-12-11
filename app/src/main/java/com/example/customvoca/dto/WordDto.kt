package com.example.customvoca.dto

import java.io.Serializable
import java.sql.Timestamp

data class WordDto (
    var wordId: Int,
    var vocabId: Int,
    var expression: String,
    var createdAt: Timestamp
)

class CreateWord {
    data class Request(
        var expression: String
    )

    data class Response(
        var wordId: Int,
        var vocabId: Int,
        var expression: String,
        var createdAt: Timestamp
    )
}

data class WordWithDef(
    val wordId: Int,
    val expression: String,
    val definition: String,
    val type: String
): Serializable
