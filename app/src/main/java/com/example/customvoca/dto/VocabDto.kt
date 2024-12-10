package com.example.customvoca.dto

import java.sql.Timestamp

class VocabDto (
    var vocabId: Int,
    var userId: Int,
    var title: String,
    var description: String,
    var wordCount: Int,
    var createdAt: Timestamp
)

class CreateVocab {
    data class Request(
        var title: String,
        var description: String
    )

    data class Response(
        var vocabId: Int,
        var title: String,
        var description: String,
        var createdAt: Timestamp
    )
}