package com.example.listycity

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

data class City(
    val name: String = "",
    val province: String = "",
    @set:DocumentId
    var uniqueID: String = ""
)