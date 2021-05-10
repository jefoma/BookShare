package com.example.bookshare.data.model

class User {
    var correo: String
    var username: String

    constructor(correo: String, username: String) {
        this.correo = correo
        this.username = username
    }
    fun getQuestion(): String {
        return correo + "value"
    }

    fun setQuestion(question: String) {
        this.correo = question + "value"
    }

}