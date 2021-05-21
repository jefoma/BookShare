package com.example.bookshare


//Esta clase nos sirve para guardar libros de manera facil al realizar algunas de las querys que
// necesitaremos hacer a lo largo del desarrollo de la aplicación
class Libro {
    //Declaramos las variables que tendra la clase Libro(), estas deben tener exactamente el mismo nombre que los campos de la BBDD
    var Titulo: String? = null
    var ISBN: String? = null
    var Autor: String? = null
    var Sinopsis: String? = null
    var Idioma: String? = null
    var Editorial: String? = null
    var Ciudad: String? = null
    var CP: String? = null
    var Propietario: String? = null

    //Creamos un constructor con todas las variables
    constructor(Titulo: String?, Editorial: String?, ISBN: String?, Autor: String?, Sinopsis: String?, Idioma: String?, Ciudad: String?, CP: String?, Propietario: String?) {
        this.Titulo = Titulo
        this.ISBN = ISBN
        this.Autor = Autor
        this.Sinopsis = Sinopsis
        this.Idioma = Idioma
        this.Editorial = Editorial
        this.Ciudad = Ciudad
        this.CP = CP
        this.Propietario = Propietario

    }
    //Creamos un constructor vacio que necistaremos para declarar los Objetos Libro() vacios
    constructor() {}

}
