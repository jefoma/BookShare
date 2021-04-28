package com.example.bookshare

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registro : AppCompatActivity(), View.OnClickListener {

    private var sing_up: Button? = null
    private var correoElectronico: EditText? = null
    private var nombreDeUsuario: EditText? = null
    private var contraseña: EditText? = null

    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        sing_up = findViewById<Button>(R.id.sing_up);
        correoElectronico = findViewById<EditText>(R.id.correoElectronico);
        contraseña = findViewById<EditText>(R.id.contraseña);
        nombreDeUsuario = findViewById<EditText>(R.id.nombreDeUsuario);


        sing_up!!.setOnClickListener(this)
        correoElectronico!!.setOnClickListener(this)
        contraseña!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sing_up -> {
                if (correoElectronico!!.text.isNotEmpty() && contraseña?.text!!.isNotEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(correoElectronico!!.text.toString(),
                            contraseña!!.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            db.collection("users").document(correoElectronico!!.text.toString()).set(
                                    hashMapOf("username" to nombreDeUsuario!!.text.toString())
                            )

                            val intent: Intent = Intent(this, Perfil::class.java).apply {
                                putExtra("username", nombreDeUsuario!!.text.toString())
                            }
                            startActivity(intent)

                        } else {
                            showAlert()
                        }
                    }

                }
            }
        }
    }
    /*Funcion que hacec saltar una ventana con un boton. La ventana pone error*/

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error, No se ha podido crear el usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
}