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
    //Declaramos las variables de todos los items que tenemos en la activity
    private var nombreDellibrorEditText: TextView? = null
    private var nombreDelAutorEditText: TextView? = null
    private var editorialEditText: TextView? = null
    private var idiomaEditText: TextView? = null
    private var ISBNEditText: TextView? = null
    private var sinopsisEditText: TextView? = null
    private var ciudadEditText: TextView? = null
    private var CPEditText: TextView? = null
    private var btnAgregaLibro: Button? = null
    //Declaramos la variable que usaremos para realizar la conexiona la BBDD
    private var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_libro)
        //Asignamos los items de la activity a las variables que declaramos anteriormente
        nombreDellibrorEditText = findViewById<TextView>(R.id.nombreDellibrorEditText)
        editorialEditText = findViewById<TextView>(R.id.editorialEditText)
        idiomaEditText = findViewById<TextView>(R.id.idiomaEditText)
        nombreDelAutorEditText = findViewById<TextView>(R.id.nombreDelAutorEditText)
        ISBNEditText = findViewById<TextView>(R.id.ISBNEditText)
        sinopsisEditText = findViewById<TextView>(R.id.sinopsisEditText)
        ciudadEditText = findViewById<TextView>(R.id.ciudadEditText)
        CPEditText = findViewById<TextView>(R.id.CPEditText)
        btnAgregaLibro = findViewById<Button>(R.id.btnAgregaLibro)
        //Asignamos el listener al Button que añadira los libros a la BBDD
        btnAgregaLibro!!.setOnClickListener(this)

    }
    //Creamos la función donde añadiremos las acciones que deben realizar los items con listeners
    override fun onClick(v: View?) {
        when (v?.id) {
            //Agregaremos el libro a la BBDD
            R.id.btnAgregaLibro -> {
                //Indicamos a la BBDD en que colección queremos que haga el Insert
                val libroRef = database.collection("libros")
                //Guardamos el usuario con el que estamos conectados en una variable para añadirlo
                // automaticamente al atributo Propietario de la coleccion Libros
                var user = FirebaseAuth.getInstance().currentUser
                var email = user?.email!!.toString()
                //Creamos un HashMap con los datos que el usuario ha rellenado en los EditText
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
                //Hacemos el insert a la BBDD
                libroRef.document().set(insert)
                //Creamos un aviso de que el libro se añadió correctamente a la BBDD
                Toast.makeText(this, "Sea ha añadido el libro correctamente", Toast.LENGTH_SHORT).show()
                //Volvemos a la activity de Catalogo
                val intent = Intent(this, Catalogo::class.java)
                startActivity(intent)
            }
        }
    }
}

