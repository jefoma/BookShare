package com.example.bookshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class EditarLibro: AppCompatActivity() , View.OnClickListener{
    //Declaramos las variables de todos los items que tenemos en la activity
    private var nombreDellibrorEditText: TextView? = null
    private var nombreDelAutorEditText: TextView? = null
    private var editorialEditText: TextView? = null
    private var idiomaEditText: TextView? = null
    private var ISBNEditText: TextView? = null
    private var sinopsisEditText: TextView? = null
    private var ciudadEditText: TextView? = null
    private var CPEditText: TextView? = null
    private var btnEditarLibro: Button? = null
    //Creamos una variable de tipo String para guardar el ID del libro que llega al bundle de la activity
    var IDLibro: String = ""
    //Declaramos la variable que usaremos para realizar la conexiona la BBDD
    private var database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_libro)
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
        btnEditarLibro = findViewById<Button>(R.id.btnEditarLibro)
        //Asignamos el listener al Button que hara el update del libro a la BBDD
        btnEditarLibro!!.setOnClickListener(this)
        //Llamamos a la funcion cargaDatosBD() para que rellene los datos actuales del libro
        cargaDatosBD()

    }
    //Creamos la función donde añadiremos las acciones que deben realizar los items con listeners
    override fun onClick(v: View?) {
        when (v?.id) {
            //Editaremos el libro
            R.id.btnEditarLibro -> {
                //Indicamos a la BBDD en que registro queremos realizar el update usando la variable que recojimos del Bundle
                val libroRef = database.collection("libros").document(IDLibro)
                //realizamos los updates de cada campo para que se modifiquen los datos del libro
                libroRef.update("Autor", nombreDelAutorEditText!!.text.toString())
                libroRef.update("CP", CPEditText!!.text.toString())
                libroRef.update("Editorial", editorialEditText!!.text.toString())
                libroRef.update("Idioma", idiomaEditText!!.text.toString())
                libroRef.update("Titulo", nombreDellibrorEditText!!.text.toString())
                libroRef.update("ISBN", ISBNEditText!!.text.toString())
                libroRef.update("Sinopsis", sinopsisEditText!!.text.toString())
                libroRef.update("Ciudad", ciudadEditText!!.text.toString())
                //Llamamos otra vez a la función cargaDatos() para que muestre los nuevos datos guardados
                cargaDatosBD()
                ////Creamos un aviso de que el libro se edito correctamente en la BBDD
                Toast.makeText(this, "Libro actualizado", Toast.LENGTH_SHORT).show()
                //Volvemos a la activity de Catalogo
                val intent = Intent(this, Catalogo::class.java)
                startActivity(intent)
            }
        }
    }
    //Esta funcion muestra todos los datos del libro que queremos editar y los muestra en los EditText correspondientes
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
            } else {
                Log.d("Error", "No Existe")
            }
        }
    }
}

