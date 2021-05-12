package com.example.bookshare

class Libro {
    var titulo: String? = null
    var isbn: String? = null
    var autor: String? = null
    var sinopsis: String? = null
    var idioma: String? = null
    var editorial: String? = null

    constructor(titulo: String?, editorial: String?, isbn: String?, autor: String?, sinopsis: String?, idioma: String?) {
        this.titulo = titulo
        this.isbn = isbn
        this.autor = autor
        this.sinopsis = sinopsis
        this.idioma = idioma
        this.editorial = editorial

    }

    constructor() {}

}
