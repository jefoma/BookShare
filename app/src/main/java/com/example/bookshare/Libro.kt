package com.example.bookshare

class Libro {
    var Titulo: String? = null
    var ISBN: String? = null
    var Autor: String? = null
    var Sinopsis: String? = null
    var Idioma: String? = null
    var Editorial: String? = null
    var Ciudad: String? = null
    var CP: String? = null
    var Propietario: String? = null

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

    constructor() {}

}
