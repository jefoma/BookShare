package com.example.bookshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class Chats : AppCompatActivity(), View.OnClickListener {

    private var imagenBook: ImageView? = null
    private var imagenPerfil: ImageView? = null
    private var imagenChats: ImageView? = null
    private var imagenCerrarSession: ImageView? = null
    private var nombreDeContacto1: TextView? = null
    private var nombreDeContacto2: TextView? = null
    private var nombreDeContacto3: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        imagenBook = findViewById<ImageView>(R.id.imagenBook)
        imagenPerfil = findViewById<ImageView>(R.id.imagenPerfil)
        imagenChats = findViewById<ImageView>(R.id.imagenChats)
        imagenCerrarSession = findViewById<ImageView>(R.id.imagenCerrarSession)
        nombreDeContacto1 = findViewById<TextView>(R.id.nombreDeContacto1)
        nombreDeContacto2 = findViewById<TextView>(R.id.nombreDeContacto2)
        nombreDeContacto3 = findViewById<TextView>(R.id.nombreDeContacto3)


        imagenBook!!.setOnClickListener(this)
        imagenPerfil!!.setOnClickListener(this)
        imagenChats!!.setOnClickListener(this)
        imagenCerrarSession!!.setOnClickListener(this)
        nombreDeContacto1!!.setOnClickListener(this)
        nombreDeContacto2!!.setOnClickListener(this)
        nombreDeContacto3!!.setOnClickListener(this)



    }
    override fun onClick(v: View?) {
        when (v?.id) {
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
            R.id.nombreDeContacto1 -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, Chat::class.java)
                startActivity(intent)
            }
            R.id.nombreDeContacto2 -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, Chat::class.java)
                startActivity(intent)
            }
            R.id.nombreDeContacto3 -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, Chat::class.java)
                startActivity(intent)
            }
        }
    }
}