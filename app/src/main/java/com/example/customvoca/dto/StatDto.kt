package com.example.customvoca.dto

data class StatDto (
    var wordId: Int,
    var correctCount: Int,
    var incorrectCount: Int,
    var isLearned: Int
)

class UpdateStat {
    data class Request (
        var correctCount: Int,
        var incorrectCount: Int,
        var isLearned: Int
    )

    data class Response (
        var wordId: Int,
        var correctCount: Int,
        var incorrectCount: Int,
        var isLearned: Int
    )
}