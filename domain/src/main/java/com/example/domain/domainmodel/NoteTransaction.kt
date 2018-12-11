package com.example.domain.domainmodel

/**
 * Created by Festus Kiambi on 12/11/18.
 */
data class NoteTransaction(

    val creationDate: String,
    val contents: String,
    val upVotes: Int,
    val imageUrl: String,
    val creator: User?,
    val transactionType: TransactionType
)

enum class TransactionType(val value: String) {
    UPDATE("update"),
    DELETE("delete"),
}

fun Note.toTransaction(type: TransactionType): NoteTransaction = NoteTransaction(
    creationDate,
    contents,
    upVotes,
    imageUrl,
    creator,
    type
)