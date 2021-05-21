package com.example.bookshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUserMetadata


class MainActivity : AppCompatActivity(),  View.OnClickListener {
    //Declaramos las variables de todos los items que tenemos en la activity
    private var login: Button? = null
    private var sing_up: Button? = null
    private var correoElectronico: EditText? = null
    private var contraseña: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Asignamos los items de la activity a las variables que declaramos anteriormente
        login = findViewById<Button>(R.id.login);
        sing_up = findViewById<Button>(R.id.sing_up);
        correoElectronico = findViewById<EditText>(R.id.correoElectronico);
        contraseña = findViewById<EditText>(R.id.contraseña);
        //Asignaos listeners a los items que lo requieran
        login!!.setOnClickListener(this)
        sing_up!!.setOnClickListener(this)
        correoElectronico!!.setOnClickListener(this)
        contraseña!!.setOnClickListener(this)
    }
    //Creamos la función donde añadiremos las acciones que deben realizar los items con listeners
    override fun onClick(v: View?) {
        when (v?.id) {
            //Nos permite logearnos a la aplicacion
            R.id.login -> {
                //Si el correo electronico y la contrasena que escribio el usuario en los EditText
                // coinciden con algun usuario registrado nos conectamos a la BBDD
                if (correoElectronico!!.text.isNotEmpty() && contraseña?.text!!.isNotEmpty()){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(correoElectronico!!.text.toString(),
                            contraseña!!.text.toString()).addOnCompleteListener{
                        if (it.isSuccessful){
                            //Si los datos son correctos llamamos la activity Perfil
                            val user = FirebaseAuth.getInstance().currentUser
                            val intent: Intent = Intent(this, Catalogo::class.java)
                            startActivity(intent)
                        }else{
                            //de no ser correctos mostraremos una alerta
                            showAlert()
                        }
                    }

                }

            }
            //Incia la activity registro
            R.id.sing_up -> {
                val intent: Intent = Intent(this, Registro::class.java)
                startActivity(intent)
            }
        }
    }

    //Creamos una función para mostrar una alerta en caso de que se oproduzca un error
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al intentar acceder")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
}



