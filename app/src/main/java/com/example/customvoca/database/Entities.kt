package com.example.customvoca.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocas")
data class Voca(
        var word: String,
        var meaning: String,
        var tableId: Int = 0
    ) {
    @PrimaryKey(autoGenerate = true)
    var vocaId: Int = 0 //단어 기본키
    var type: String = "" //en, kr, jp, ch 등
    override fun toString(): String {
        return vocaId.toString()+"/"+tableId+"/"+word+"/"+meaning+"/"+type
    }
}

@Entity(tableName = "voca_list")
data class VocaList(
        var title: String
    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 //단어장 인덱스
    override fun toString(): String {
        return id.toString()+"/"+title
    }
}