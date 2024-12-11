package com.example.customvoca.type

enum class POS(
    val type: String
) {
    NOUN("명사"),
    PRONOUN("대명사"),
    VERB("동사"),
    ADJECTIVE("형용사"),
    ADVERB("부사"),
    ARTICLE("관사"),
    PREPOSITION("전치사"),
    CONJUNCTION("접속사"),
    INTERJECTION("감탄사");

    companion object {
        fun fromName(name: String): POS? {
            return values().find { it.type == name } // type이 name과 일치하는 POS를 찾음
        }
    }
}