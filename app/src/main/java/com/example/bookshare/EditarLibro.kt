package com.example.bookshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class EditarLibro: AppCompatActivity() , View.OnClickListener{
    private var nombreDellibrorEditText: TextView? = null
    private var nombreDelAutorEditText: TextView? = null
    private var editorialEditText: TextView? = null
    private var idiomaEditText: TextView? = null
    private var ISBNEditText: TextView? = null
    private var sinopsisEditText: TextView? = null
    private var ciudadEditText: TextView? = null
    private var CPEditText: TextView? = null
    private var btnEditarLibro: Button? = null

    var IDLibro: String = ""
    private var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_libro)

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
        btnEditarLibro = findViewById<Button>(R.id.btnEditarLibro)

        btnEditarLibro!!.setOnClickListener(this)

        cargaDatosBD()

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnEditarLibro -> {
                val libroRef = database.collection("libros").document(IDLibro)
                libroRef.update("Autor", nombreDelAutorEditText!!.text.toString())
                libroRef.update("CP", CPEditText!!.text.toString())
                libroRef.update("Editorial", editorialEditText!!.text.toString())
                libroRef.update("Idioma", idiomaEditText!!.text.toString())
                libroRef.update("Titulo", nombreDellibrorEditText!!.text.toString())
                libroRef.update("ISBN", ISBNEditText!!.text.toString())
                libroRef.update("Sinopsis", sinopsisEditText!!.text.toString())
                libroRef.update("Ciudad", ciudadEditText!!.text.toString())
                cargaDatosBD()

                Toast.makeText(this, "Libro actualizado", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Catalogo::class.java)
                startActivity(intent)
            }
        }

    }
    fun cargaDatosBD() {
        val libroRef = database.collection("libros").document(IDLibro)
        Log.d("HOLA", IDLibro)

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

            } else {
                Log.d("Error", "No Existe")

            }
        }

        /*libroRef.get().addOnSuccessListener { document ->
            if (document != null) {
                for (doc in document) {
                    libro.autor = doc["Autor"].toString()
                    libro.editorial = doc["Editorial"].toString()
                    libro.idioma = doc["Idioma"].toString()
                    libro.isbn = doc["ISBN"].toString()
                    libro.sinopsis = doc["Sinopsis"].toString()
                    libro.titulo = doc["Titulo"].toString()
                    libro.cp = doc["CP"].toString()
                    libro.ciudad = doc["Ciudad"].toString()
                    libro.propietario = doc["Propietario"].toString()

                    nombreDellibrorEditText?.setText(libro?.titulo)
                    nombreDelAutorEditText?.setText(libro?.autor)
                    editorialEditText?.setText(libro?.editorial)
                    idiomaEditText?.setText(libro?.idioma)
                    ISBNEditText?.setText(libro?.isbn)
                    sinopsisEditText?.setText(libro?.sinopsis)
                    ciudadEditText?.setText(libro?.ciudad)
                    CPEditText?.setText(libro?.cp)
                }

            } else {
                Log.d("Error", "No Existe")

            }
        }*/
    }
}

