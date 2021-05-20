package com.example.bookshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Catalogo : AppCompatActivity(), View.OnClickListener {

    private lateinit var listView: ListView
    private var database = FirebaseFirestore.getInstance()
    val listTitulos: MutableList<String> = ArrayList()
    val listPropietarios: MutableList<String> = ArrayList()
    val listIDLibros: MutableList<String> = ArrayList()
    private var btnAgregaLibroCatalogo: ImageView? = null
    private var imagenBook: ImageView? = null
    private var imagenPerfil: ImageView? = null
    private var imagenChats: ImageView? = null
    private var imagenCerrarSession: ImageView? = null
    private var btnMisLibros: ImageView? = null
    private var btnLupa: ImageView? = null
    private var nombreDelLibroEditText: EditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)
        listView = findViewById<ListView>(R.id.listView)
        btnAgregaLibroCatalogo = findViewById<ImageView>(R.id.btnAgregaLibroCatalogo)
        imagenBook = findViewById<ImageView>(R.id.imagenBook)
        imagenPerfil = findViewById<ImageView>(R.id.imagenPerfil)
        imagenChats = findViewById<ImageView>(R.id.imagenChats)
        imagenCerrarSession = findViewById<ImageView>(R.id.imagenCerrarSession)
        btnMisLibros = findViewById<ImageView>(R.id.btnMisLibros)
        btnLupa = findViewById<ImageView>(R.id.btnLupa)
        nombreDelLibroEditText = findViewById<EditText>(R.id.nombreDelLibroEditText);


        imagenBook!!.setOnClickListener(this)
        btnAgregaLibroCatalogo!!.setOnClickListener(this)
        imagenPerfil!!.setOnClickListener(this)
        imagenChats!!.setOnClickListener(this)
        imagenCerrarSession!!.setOnClickListener(this)
        btnMisLibros!!.setOnClickListener(this)
        btnLupa!!.setOnClickListener(this)

        cargaDatosBD()

    }

    fun cargaDatosBD() {
        val usersRef = database.collection("libros")
        usersRef.get().addOnSuccessListener { document ->
            if (document != null) {
                for (doc in document) {
                    var titulo = doc["Titulo"].toString()
                    var id = doc?.id!!.toString()
                    listTitulos.add(titulo)
                    listIDLibros.add(id)
                    var nombrePropietario = doc["Propietario"].toString()
                    listPropietarios.add(nombrePropietario)
                    val adapter: ArrayAdapter<String> = ArrayAdapter(
                            this,
                            android.R.layout.simple_dropdown_item_1line, listTitulos
                    )
                    listView.adapter = adapter
                    listView.setOnItemClickListener { parent, view, position, id ->
                        val element = adapter.getItem(position) // The item that was clicked
                        val bundle = Bundle()
                        bundle.putString("Key", listIDLibros.get(position))

                        var user = FirebaseAuth.getInstance().currentUser
                        var email = user?.email!!.toString()

                        if (email.equals(listPropietarios.get(position))) {
                            val intent = Intent(this, EditarLibro::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this, PerfilLibro::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }

                    }
                }
            } else {
                Log.d("Error", "No Existe")

            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAgregaLibroCatalogo -> {
                val intent = Intent(this, AgregarLibro::class.java)
                val bundle = Bundle()
                bundle.putString("Key1", "Agregar")
                startActivity(intent)
            }
            R.id.imagenBook -> {
                val intent = Intent(this, Catalogo::class.java)
                startActivity(intent)
            }
            R.id.imagenPerfil -> {
                val intent = Intent(this, Perfil::class.java)
                startActivity(intent)
            }
            R.id.imagenChats -> {
                val intent = Intent(this, Chats::class.java)
                startActivity(intent)
            }
            R.id.imagenCerrarSession -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btnMisLibros -> {
                var user = FirebaseAuth.getInstance().currentUser
                var email = user?.email!!.toString()
                listTitulos.clear()
                listIDLibros.clear()
                listPropietarios.clear()
                val usersRef = database.collection("libros").whereEqualTo("Propietario", email)
                usersRef.get().addOnSuccessListener { document ->
                    if (document != null) {
                        for (doc in document) {
                            var titulo = doc["Titulo"].toString()
                            var id = doc?.id!!.toString()
                            listTitulos.add(titulo)
                            listIDLibros.add(id)
                            var nombrePropietario = doc["Propietario"].toString()
                            listPropietarios.add(nombrePropietario)
                            val adapter: ArrayAdapter<String> = ArrayAdapter(
                                    this,
                                    android.R.layout.simple_dropdown_item_1line, listTitulos
                            )
                            listView.adapter = adapter
                            listView.setOnItemClickListener { parent, view, position, id ->
                                val element = adapter.getItem(position) // The item that was clicked
                                val bundle = Bundle()
                                bundle.putString("Key", listIDLibros.get(position))

                                var user = FirebaseAuth.getInstance().currentUser
                                var email = user?.email!!.toString()

                                if (email.equals(listPropietarios.get(position))) {
                                    val intent = Intent(this, EditarLibro::class.java)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this, PerfilLibro::class.java)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                }

                            }
                        }
                    } else {
                        Log.d("Error", "No Existe")

                    }
                }
            }
            R.id.btnLupa -> {
                listTitulos.clear()
                listIDLibros.clear()
                listPropietarios.clear()
                //val usersRef1 = database.collection("libros")
                val usersRef = database.collection("libros").whereEqualTo("Titulo",  nombreDelLibroEditText!!.text.toString())
                usersRef.get().addOnSuccessListener { document ->
                    if (document != null) {
                        for (doc in document) {
                            var titulo = doc["Titulo"].toString()
                            var id = doc?.id!!.toString()
                            listTitulos.add(titulo)
                            listIDLibros.add(id)
                            var nombrePropietario = doc["Propietario"].toString()
                            listPropietarios.add(nombrePropietario)
                            val adapter: ArrayAdapter<String> = ArrayAdapter(
                                    this,
                                    android.R.layout.simple_dropdown_item_1line, listTitulos
                            )
                            listView.adapter = adapter
                            listView.setOnItemClickListener { parent, view, position, id ->
                                val element = adapter.getItem(position) // The item that was clicked
                                val bundle = Bundle()
                                bundle.putString("Key", listIDLibros.get(position))

                                var user = FirebaseAuth.getInstance().currentUser
                                var email = user?.email!!.toString()
                                //Linea magica
                                adapter.notifyDataSetChanged()
                                if (email.equals(listPropietarios.get(position))) {
                                    val intent = Intent(this, EditarLibro::class.java)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this, PerfilLibro::class.java)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                }

                            }
                        }
                    } else {
                        Log.d("Error", "No Existe")

                    }
                }
            }
        }
    }
}

