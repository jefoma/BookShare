package com.example.bookshare

//Esta clase nos sirve para guardar usuarios de manera facil al realizar algunas de las querys que
// necesitaremos hacer a lo largo del desarrollo de la aplicación
class Usuario {
    //Declaramos las variables que tendra la clase Usuario(), estas deben tener exactamente el mismo nombre que los campos de la BBDD
    var sMail: String? = null
    var username: String? = null
    var nombre: String? = null
    var apellido: String? = null
    var ciudad: String? = null
    var codigoPostal: String? = null

    //Creamos un constructor con todas las variables
    constructor(sMail: String?, username: String?, nombre: String?, apellido: String?, ciudad: String?, codigoPostal: String?) {
        this.sMail = sMail
        this.username = username
        this.nombre = nombre
        this.apellido = apellido
        this.ciudad = ciudad
        this.codigoPostal = codigoPostal

    }
    //Creamos un constructor vacio que necistaremos para declarar los Objetos Usuario() vacios
    constructor() {}

}
