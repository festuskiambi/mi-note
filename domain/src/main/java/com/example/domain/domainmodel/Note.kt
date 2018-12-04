package com.example.domain.domainmodel

/**
 * Created by Festus Kiambi on 12/3/18.
 */
class Note(val creationDate: String, val contents: String, val upVotes: Int, val imageUrl: String, val creator: User?) {

    override fun hashCode(): Int {
        return super.hashCode()
    }

}