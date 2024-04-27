package com.example.customvoca.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_list")
data class Word(
        var word: String,
        var meaning: String,
        var dic_id: Int = 0
    ) {
    @PrimaryKey(autoGenerate = true)
    var word_id: Int = 0 //단어 기본키
    var lan: String = "" //en, kr, jp, ch 등
    override fun toString(): String {
        return word_id.toString()+"/"+dic_id+"/"+word+"/"+meaning+"/"+lan
    }
}

@Entity(tableName = "dic_list")
data class Dic(
        var name: String
    ) {
    @PrimaryKey(autoGenerate = true)
    var dic_id: Int = 0 //단어장 인덱스
    override fun toString(): String {
        return dic_id.toString()+"/"+name
    }
}