package com.example.bookshare

/*import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth*/
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(),  View.OnClickListener {

    private var login: Button? = null
    private var sing_up: Button? = null
    private var correoElectronico: EditText? = null
    private var contraseña: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Iniciamos los botenes y creamos sus listeners para crear acciones
        login = findViewById<Button>(R.id.login);
        sing_up = findViewById<Button>(R.id.sing_up);
        correoElectronico = findViewById<EditText>(R.id.correoElectronico);
        contraseña = findViewById<EditText>(R.id.contraseña);


        login!!.setOnClickListener(this)
        sing_up!!.setOnClickListener(this)
        correoElectronico!!.setOnClickListener(this)
        contraseña!!.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                if (correoElectronico!!.text.isNotEmpty() && contraseña?.text!!.isNotEmpty()){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(correoElectronico!!.text.toString(),
                            contraseña!!.text.toString()).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent: Intent = Intent(this, Perfil::class.java)
                            startActivity(intent)

                        }else{
                            showAlert()
                        }
                    }

                }

            }
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



