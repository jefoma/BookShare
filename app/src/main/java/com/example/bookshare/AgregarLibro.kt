package com.example.bookshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AgregarLibro: AppCompatActivity() , View.OnClickListener{
    private var nombreDellibrorEditText: TextView? = null
    private var nombreDelAutorEditText: TextView? = null
    private var editorialEditText: TextView? = null
    private var idiomaEditText: TextView? = null
    private var ISBNEditText: TextView? = null
    private var sinopsisEditText: TextView? = null
    private var ciudadEditText: TextView? = null
    private var CPEditText: TextView? = null
    private var btnAgregaLibro: Button? = null

    var nombreLibro: String? = null
    var bundleString: String? = null
    private var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_libro)

        nombreDellibrorEditText = findViewById<TextView>(R.id.nombreDellibrorEditText)
        editorialEditText = findViewById<TextView>(R.id.editorialEditText)
        idiomaEditText = findViewById<TextView>(R.id.idiomaEditText)
        nombreDelAutorEditText = findViewById<TextView>(R.id.nombreDelAutorEditText)
        ISBNEditText = findViewById<TextView>(R.id.ISBNEditText)
        sinopsisEditText = findViewById<TextView>(R.id.sinopsisEditText)
        ciudadEditText = findViewById<TextView>(R.id.ciudadEditText)
        CPEditText = findViewById<TextView>(R.id.CPEditText)
        btnAgregaLibro = findViewById<Button>(R.id.btnAgregaLibro)

        btnAgregaLibro!!.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAgregaLibro -> {
                val libroRef = database.collection("libros")
                var user = FirebaseAuth.getInstance().currentUser
                var email = user?.email!!.toString()
                val insert = hashMapOf(
                        "Autor" to nombreDelAutorEditText!!.text.toString(),
                        "CP" to CPEditText!!.text.toString(),
                        "Editorial" to editorialEditText!!.text.toString(),
                        "Idioma" to idiomaEditText!!.text.toString(),
                        "Titulo" to nombreDellibrorEditText!!.text.toString(),
                        "ISBN" to ISBNEditText!!.text.toString(),
                        "Sinopsis" to sinopsisEditText!!.text.toString(),
                        "Ciudad" to ciudadEditText!!.text.toString(),
                        "Propietario" to email
                )
                libroRef.document().set(insert)
                Toast.makeText(this, "Sea ha añadido el libro correctamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Catalogo::class.java)
                startActivity(intent)
            }
        }
    }
}

