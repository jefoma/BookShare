package com.example.bookshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class PerfilLibro : AppCompatActivity() {
    private var nombreDellibrorEditText: TextView? = null
    private var nombreDelAutorEditText: TextView? = null
    private var editorialEditText: TextView? = null
    private var idiomaEditText: TextView? = null
    private var ISBNEditText: TextView? = null
    private var sinopsisEditText: TextView? = null
    private var ciudadEditText: TextView? = null
    private var CPEditText: TextView? = null
    private var emailEditText: TextView? = null



    private var database = FirebaseFirestore.getInstance()
    var IDLibro: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_libro)
        val bundle = intent.extras
        IDLibro = bundle!!.getString("Key", "Default")

        nombreDellibrorEditText = findViewById<TextView>(R.id.nombreDellibrorEditText)
        editorialEditText = findViewById<TextView>(R.id.editorialEditText)
        idiomaEditText = findViewById<TextView>(R.id.idiomaEditText)
        nombreDelAutorEditText = findViewById<TextView>(R.id.nombreDelAutorEditText)
        ISBNEditText = findViewById<TextView>(R.id.ISBNEditText)
        sinopsisEditText = findViewById<TextView>(R.id.sinopsisEditText)
        ciudadEditText = findViewById<TextView>(R.id.ciudadEditText)
        CPEditText = findViewById<TextView>(R.id.CPEditText)
        emailEditText = findViewById<TextView>(R.id.emailEditText)

        cargaDatosBD()
    }

    fun cargaDatosBD() {
        val libroRef = database.collection("libros").document(IDLibro)

        libroRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val libro = document.toObject(Libro::class.java)
                Log.d("HOL2", libro?.Titulo.toString())

                nombreDellibrorEditText?.setText(libro?.Titulo)
                nombreDelAutorEditText?.setText(libro?.Autor)
                editorialEditText?.setText(libro?.Editorial)
                idiomaEditText?.setText(libro?.Idioma)
                ISBNEditText?.setText(libro?.ISBN)
                sinopsisEditText?.setText(libro?.Sinopsis)
                ciudadEditText?.setText(libro?.Ciudad)
                CPEditText?.setText(libro?.CP)
                emailEditText?.setText(libro?.Propietario)

            } else {
                Log.d("Error", "No Existe")

            }
        }

    }
}