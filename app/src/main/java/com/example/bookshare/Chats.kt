package com.example.bookshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth

class Chats : AppCompatActivity(), View.OnClickListener {

    private var imagenBook: ImageView? = null
    private var imagenPerfil: ImageView? = null
    private var imagenChats: ImageView? = null
    private var imagenCerrarSession: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        imagenBook = findViewById<ImageView>(R.id.imagenBook)
        imagenPerfil = findViewById<ImageView>(R.id.imagenPerfil)
        imagenChats = findViewById<ImageView>(R.id.imagenChats)
        imagenCerrarSession = findViewById<ImageView>(R.id.imagenCerrarSession)


        imagenBook!!.setOnClickListener(this)
        imagenPerfil!!.setOnClickListener(this)
        imagenChats!!.setOnClickListener(this)
        imagenCerrarSession!!.setOnClickListener(this)


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
        }
    }
}