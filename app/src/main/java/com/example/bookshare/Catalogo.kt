package com.example.bookshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Catalogo : AppCompatActivity() {

    private lateinit var listView :ListView
    private var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)
        listView = findViewById<ListView>(R.id.listView)
        cargaDatosBD()

    }

    fun cargaDatosBD() {
        val usersRef = database.collection("libros")
        val libros = arrayOf<String>()
        var i = 0
        val list : MutableList<String> = ArrayList()
        usersRef.get().addOnSuccessListener { document ->
            if (document != null) {
                for(doc in document){
                    var titulo = doc["Titulo"].toString()
                    Log.d("HOLA1", titulo)
                    list.add(titulo)
                    i++
                    val adapter: ArrayAdapter<String> = ArrayAdapter(
                            this,
                            android.R.layout.simple_dropdown_item_1line,list
                    )
                    listView.adapter = adapter
                    listView.setOnItemClickListener { parent, view, position, id ->
                        val element = adapter.getItem(position) // The item that was clicked
                        val intent = Intent(this, PerfilLibro::class.java)
                        startActivity(intent)
                    }
                }
            } else {
                Log.d("Error", "No Existe")

            }
        }

    }
}


