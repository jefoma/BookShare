package com.example.bookshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Perfil : AppCompatActivity(), View.OnClickListener {

    //Declaramos las variables de todos los items que tenemos en la activity
    private var nombreDeUsuarioPerfl: TextView? = null
    private var nombreEditText: EditText? = null
    private var apellidosEditText: EditText? = null
    private var ciudadEditText: EditText? = null
    private var codigoPostalEditText: EditText? = null
    private var guardarDatos: Button? = null
    private var imagenBook: ImageView? = null
    private var imagenPerfil: ImageView? = null
    private var imagenChats: ImageView? = null
    private var imagenCerrarSession: ImageView? = null

    //Declaramos la variable para realizar la conexion a la BBDD
    private var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        //Asignamos los items de la activity a las variables que declaramos anteriormente
        guardarDatos = findViewById<Button>(R.id.guardarDatos);
        guardarDatos!!.setOnClickListener(this)
        imagenBook = findViewById<ImageView>(R.id.imagenBook)
        imagenPerfil = findViewById<ImageView>(R.id.imagenPerfil)
        imagenChats = findViewById<ImageView>(R.id.imagenChats)
        imagenCerrarSession = findViewById<ImageView>(R.id.imagenCerrarSession)
        nombreDeUsuarioPerfl = findViewById<TextView>(R.id.nombreDeUsuarioPerfl)
        nombreEditText = findViewById<EditText>(R.id.nombreEditText)
        apellidosEditText = findViewById<EditText>(R.id.apellidosEditText)
        ciudadEditText = findViewById<EditText>(R.id.ciudadEditText)
        codigoPostalEditText = findViewById<EditText>(R.id.codigoPostalEditText)

        //Asignaos listeners a los items que lo requieran
        imagenBook!!.setOnClickListener(this)
        imagenPerfil!!.setOnClickListener(this)
        imagenChats!!.setOnClickListener(this)
        imagenCerrarSession!!.setOnClickListener(this)

        //Llamamos a la función carga datos para que rellene la ListView desde el momento en que se inicia la activity
        cargaDatosBD()

    }

    //Esta funcion carga todos los datos del usuario clogeado
    fun cargaDatosBD() {
        //Nos guardamos el correo del usuario en una variable
        var user = FirebaseAuth.getInstance().currentUser
        var email = user?.email!!.toString()
        //Creamos una consulta para conseguir los datos del usuario
        val usersRef = database.collection("users").document(email)

        usersRef.get().addOnSuccessListener { document ->
            if (document != null) {
                //guardamos el resultado de la consulta en un objeto de tipo usuario
                val user2 = document.toObject(Usuario::class.java)

                //asignamos los atributos del usuario a los EditText correspondientes
                nombreDeUsuarioPerfl?.setText(user2?.username)
                nombreEditText?.setText(user2?.nombre)
                apellidosEditText?.setText(user2?.apellido)
                ciudadEditText?.setText(user2?.ciudad)
                codigoPostalEditText?.setText(user2?.codigoPostal.toString())

            } else {
                Log.d("Error", "No Existe")

            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.guardarDatos -> {
                //Nos guardamos el correo del usuario en una variable
                var user = FirebaseAuth.getInstance().currentUser
                var email = user?.email!!.toString()
                //Creamos una consulta para conseguir los datos del usuario
                val usersRef = database.collection("users").document(email)
                //Hacemos un update con los nuevos datos que el usuario ha modificado
                usersRef.update("nombre", nombreEditText!!.text.toString())
                usersRef.update("apellido", apellidosEditText!!.text.toString())
                usersRef.update("ciudad", ciudadEditText!!.text.toString())
                usersRef.update("codigoPostal", codigoPostalEditText!!.text.toString())
                //Mostrmos un mensaje que nos indica que se ha realizado el update
                Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                //Llamamos a esta función para que se cargen los datos de nuevo
                cargaDatosBD()
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
        }
    }
}