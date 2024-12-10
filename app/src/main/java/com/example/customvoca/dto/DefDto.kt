package com.example.customvoca.dto

import com.example.customvoca.type.POS

data class DefDto (
    var defId: Int,
    var wordId: Int,
    var definition: String,
    var type: POS
)

class CreateDef {
    data class Request(
        var definition: String,
        var type: POS
    )

    data class Response(
        var defId: Int,
        var wordId: Int,
        var definition: String,
        var type: POS
    )
}