package com.example.bookshare


class Usuario {
    var sMail: String? = null
    var username: String? = null
    var nombre: String? = null
    var apellido: String? = null
    var ciudad: String? = null
    var codigoPostal: String? = null

    constructor(sMail: String?, username: String?, nombre: String?, apellido: String?, ciudad: String?, codigoPostal: String?) {
        this.sMail = sMail
        this.username = username
        this.nombre = nombre
        this.apellido = apellido
        this.ciudad = ciudad
        this.codigoPostal = codigoPostal

    }

    constructor() {}

}
