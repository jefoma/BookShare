package com.example.bookshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class PerfilLibro : AppCompatActivity() {
    //Declaramos las variables de todos los items que tenemos en la activity
    private var nombreDellibrorEditText: TextView? = null
    private var nombreDelAutorEditText: TextView? = null
    private var editorialEditText: TextView? = null
    private var idiomaEditText: TextView? = null
    private var ISBNEditText: TextView? = null
    private var sinopsisEditText: TextView? = null
    private var ciudadEditText: TextView? = null
    private var CPEditText: TextView? = null
    private var emailEditText: TextView? = null
    //Declaramos la variable que usaremos para realizar la conexiona la BBDD
    private var database = FirebaseFirestore.getInstance()
    //Creamos una variable de tipo String para guardar el ID del libro que llega al bundle de la activity
    var IDLibro: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_libro)
        //Recogemos el ID del libro que hemos pasado por Bundle
        val bundle = intent.extras
        IDLibro = bundle!!.getString("Key", "Default")
        //Asignamos los items de la activity a las variables que declaramos anteriormente
        nombreDellibrorEditText = findViewById<TextView>(R.id.nombreDellibrorEditText)
        editorialEditText = findViewById<TextView>(R.id.editorialEditText)
        idiomaEditText = findViewById<TextView>(R.id.idiomaEditText)
        nombreDelAutorEditText = findViewById<TextView>(R.id.nombreDelAutorEditText)
        ISBNEditText = findViewById<TextView>(R.id.ISBNEditText)
        sinopsisEditText = findViewById<TextView>(R.id.sinopsisEditText)
        ciudadEditText = findViewById<TextView>(R.id.ciudadEditText)
        CPEditText = findViewById<TextView>(R.id.CPEditText)
        emailEditText = findViewById<TextView>(R.id.emailEditText)
        //Llamamos a la funcion cargaDatosBD() para que rellene los datos actuales del libro
        cargaDatosBD()
    }

    //Esta funcion muestra todos los datos del libro que seleccionamos y los muestra en los TextViews correspondientes
    fun cargaDatosBD() {
        //Indicamos a la BBDD el registro que queremos que muestre usando la variable que recojimos del Bundle
        val libroRef = database.collection("libros").document(IDLibro)
        libroRef.get().addOnSuccessListener { document ->
            if (document != null) {
                //Pasamos el resultado de la query a un Objeto de la clase Libro() que emos creado
                val libro = document.toObject(Libro::class.java)
                //Asignamos a cada EditText el atributo que le corresponde
                nombreDellibrorEditText?.setText(libro?.Titulo)
                nombreDelAutorEditText?.setText(libro?.Autor)
                editorialEditText?.setText(libro?.Editorial)
                idiomaEditText?.setText(libro?.Idioma)
                ISBNEditText?.setText(libro?.ISBN)
                sinopsisEditText?.setText(libro?.Sinopsis)
                ciudadEditText?.setText(libro?.Ciudad)
                CPEditText?.setText(libro?.CP)
                emailEditText?.setText(libro?.Propietario)

            } else {
                Log.d("Error", "No Existe")

            }
        }

    }
}