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
    //Declaramos las variables de todos los items que tenemos en la activity
    private var sing_up: Button? = null
    private var correoElectronico: EditText? = null
    private var nombreDeUsuario: EditText? = null
    private var contraseña: EditText? = null
    //Declaramos la variable que usaremos para realizar la conexiona la BBDD
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        //Asignamos los items de la activity a las variables que declaramos anteriormente
        sing_up = findViewById<Button>(R.id.sing_up);
        correoElectronico = findViewById<EditText>(R.id.correoElectronico);
        contraseña = findViewById<EditText>(R.id.contraseña);
        nombreDeUsuario = findViewById<EditText>(R.id.nombreDeUsuario);
        //Asignaos listeners a los items que lo requieran
        sing_up!!.setOnClickListener(this)
        correoElectronico!!.setOnClickListener(this)
        contraseña!!.setOnClickListener(this)
    }
    //Creamos la función donde añadiremos las acciones que deben realizar los items con listeners
    override fun onClick(v: View?) {
        when (v?.id) {
            //Nos permite registrar a un usuario a nuestra BBDD
            R.id.sing_up -> {
                //Indicamos que si hay texto escrito en los EditText proceda a registrar al usuario
                if (correoElectronico!!.text.isNotEmpty() && contraseña?.text!!.isNotEmpty()) {
                    //Creamos la nueva cuenta
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(correoElectronico!!.text.toString(),
                            contraseña!!.text.toString()).addOnCompleteListener {
                        //Si se crea la cuenta añadimos el nuevo usuario a la BBD
                        if (it.isSuccessful) {
                            //Añadimos el usuario a la base de datos
                            db.collection("users").document(correoElectronico!!.text.toString()).set(
                                    hashMapOf("username" to nombreDeUsuario!!.text.toString())
                            )
                            //Llamamos a la activity Perfil
                            val intent: Intent = Intent(this, Perfil::class.java)
                            startActivity(intent)

                        } else {
                            //Si no se crea la cuenta mostramos un mensaje de error
                            showAlert()
                        }
                    }

                }
            }
        }
    }
    //Creamos una función para mostrar una alerta en caso de que se oproduzca un error
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error, No se ha podido crear el usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
}