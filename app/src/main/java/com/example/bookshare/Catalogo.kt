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

    //Declaramos la listview que necesitaremos para mostrar los libros y la conexion a la BBDD de firebase
    private lateinit var listView: ListView
    private var database = FirebaseFirestore.getInstance()

    //Declaramos las listas que necesitaremos para mostrar los libros en la ListView
    //Guarda los titulos que se mostraran
    val listTitulos: MutableList<String> = ArrayList()
    //lista los propietarios para saber si el libro es del usuario conectado y asi dejarle editarlo
    val listPropietarios: MutableList<String> = ArrayList()
    //lista el id de los libros para asi asegurar que entre en el libro clicado y no en otro ibro con el mismo titulo
    val listIDLibros: MutableList<String> = ArrayList()
    //lista donde guardamos los libros que cumplan la condición de la busqueda
    val listBusqueda: MutableList<String> = ArrayList()
    //guarda los IDs de los libros que cumplan con la busqueda
    val listBusquedaIDLibros: MutableList<String> = ArrayList()

    //Declaramos las variables de todos los items que tenemos en la activity
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
        //Asignamos los items de la activity a las variables que declaramos anteriormente
        listView = findViewById<ListView>(R.id.listView)
        btnAgregaLibroCatalogo = findViewById<ImageView>(R.id.btnAgregaLibroCatalogo)
        imagenBook = findViewById<ImageView>(R.id.imagenBook)
        imagenPerfil = findViewById<ImageView>(R.id.imagenPerfil)
        imagenChats = findViewById<ImageView>(R.id.imagenChats)
        imagenCerrarSession = findViewById<ImageView>(R.id.imagenCerrarSession)
        btnMisLibros = findViewById<ImageView>(R.id.btnMisLibros)
        btnLupa = findViewById<ImageView>(R.id.btnLupa)
        nombreDelLibroEditText = findViewById<EditText>(R.id.nombreDelLibroEditText);

        //Asignaos listeners a los items que lo requieran
        imagenBook!!.setOnClickListener(this)
        btnAgregaLibroCatalogo!!.setOnClickListener(this)
        imagenPerfil!!.setOnClickListener(this)
        imagenChats!!.setOnClickListener(this)
        imagenCerrarSession!!.setOnClickListener(this)
        btnMisLibros!!.setOnClickListener(this)
        btnLupa!!.setOnClickListener(this)

        //Llamamos a la función carga datos para que rellene la ListView desde el momento en que se inicia la activity
        cargaDatosBD()

    }

    //Esta función crea una lista con todos los libros de la BBDD y la muestra en la ListView
    fun cargaDatosBD() {
        //Con la variable de Firebase creamos una consulta donde buscamos todos los libros de la BBDD
        val usersRef = database.collection("libros")
        usersRef.get().addOnSuccessListener { document ->
            if (document != null) {
                //Recorremos todos los Registros que entran en la query y agregamos los datos necesarios a las listas para poder mostrarlos en la ListView
                for (doc in document) {
                    //Guardamos los datos que necistamos de los libros
                    var titulo = doc["Titulo"].toString()
                    var id = doc?.id!!.toString()
                    var nombrePropietario = doc["Propietario"].toString()
                    //Añadimos los datos a las listas
                    listTitulos.add(titulo)
                    listIDLibros.add(id)
                    listPropietarios.add(nombrePropietario)
                    //Llamamos a la función adapter que nos permite listar en la ListView va partir de una lista de IDs y una de Titulos
                    adapter(listTitulos, listIDLibros)
                }
            } else {
                //Añadimos un mensaje de error en caso de que fallase la consulta
                Log.d("Error", "No Existe")

            }
        }


    }
    //Esta función nos permite listar libros a partir d dos listas una de Titulos y una de IDs
    fun adapter( listaTitulos: MutableList<String>, listaIDs: MutableList<String>){
        //Creamos el adapter, que le pasaremos a la ListView, a partir de la lista de titulos( lo que queremos que se muestre en la ListView)
        val adapter: ArrayAdapter<String> = ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line, listaTitulos
        )
        //Asignamos el adapter a la ListView para que muestre los titulos de los libros
        listView.adapter = adapter
        //Creamos un Listener para cada registro de la ListView
        listView.setOnItemClickListener { parent, view, position, id ->
            //Nos guardamos en "element" el registro que ha sido clicado
            val element = adapter.getItem(position)
            //Creamos un Bundle para pasarle a la activity PerfilLibro el ID del libro que tendra que mostrar
            val bundle = Bundle()
            //Añadimos la ID al bundle
            bundle.putString("Key", listaIDs.get(position))
            //Guardamos el usuario con el que estamos conectados en una variable
            var user = FirebaseAuth.getInstance().currentUser
            var email = user?.email!!.toString()
            //Comprovamos si el usuario es propietario del libro seleccionado
            //En caso afirmativo iniciamos la activity EditarLibro
            if (email.equals(listPropietarios.get(position))) {
                val intent = Intent(this, EditarLibro::class.java)
                //Pasamos el Bundle anterior al Intent de la siguiente activity
                intent.putExtras(bundle)
                startActivity(intent)
            //De nos ser propietario del libro iniciamos la activity PerfilLibro
            } else {
                val intent = Intent(this, PerfilLibro::class.java)
                //Pasamos el Bundle anterior al Intent de la siguiente activity
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }
    }
    //Creamos la función donde añadiremos las acciones que deben realizar los items con listeners
    override fun onClick(v: View?) {
        when (v?.id) {
            //Llama la activity que se encarga de agregar libros a la BBDD
            R.id.btnAgregaLibroCatalogo -> {
                val intent = Intent(this, AgregarLibro::class.java)
                startActivity(intent)
            }
            //Inicia la activity que se encarga de mostrar el Catalogo de libros
            R.id.imagenBook -> {
                val intent = Intent(this, Catalogo::class.java)
                startActivity(intent)
            }
            //Inicia la activity que se encarga de mostrar el Perfil de usuario
            R.id.imagenPerfil -> {
                val intent = Intent(this, Perfil::class.java)
                startActivity(intent)
            }
            //Inicia la activity que se encarga de mostrar los chats
            R.id.imagenChats -> {
                val intent = Intent(this, Chats::class.java)
                startActivity(intent)
            }
            //Vuelve a la main activity y cierra la sesion de usuario actual
            R.id.imagenCerrarSession -> {
                //Esta linea cierra la sesion de usuario
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            //Muestra los libros que ha añadido a la BBDD el usuario logeado
            R.id.btnMisLibros -> {
                //Guardamos el usuario con el que estamos conectados en una variable
                var user = FirebaseAuth.getInstance().currentUser
                var email = user?.email!!.toString()
                //Limpiamos las listas para poder reutilizarlas
                listTitulos.clear()
                listIDLibros.clear()
                listPropietarios.clear()
                //Creamos la consulta a partir de los libros que tengan en el atributo Propietario el mail del usuario logeado
                val usersRef = database.collection("libros").whereEqualTo("Propietario", email)
                usersRef.get().addOnSuccessListener { document ->
                    if (document != null) {
                        //Recorremos la consulta
                        for (doc in document) {
                            //Guardamos los datos que necistamos de los libros
                            var titulo = doc["Titulo"].toString()
                            var id = doc?.id!!.toString()
                            var nombrePropietario = doc["Propietario"].toString()
                            //Añadimos los datos a las listas
                            listTitulos.add(titulo)
                            listIDLibros.add(id)
                            listPropietarios.add(nombrePropietario)
                            //Llamamos a la función adapter que nos permite listar en la ListView va partir de una lista de IDs y una de Titulos
                            adapter(listTitulos, listIDLibros)
                        }
                    } else {
                        //Añadimos un mensaje de error en caso de que fallase la consulta
                        Log.d("Error", "No Existe")

                    }
                }

            }
            //Muestra los libros que en su titulo contengan el texto escrito en el EditText que se ve en pantalla
            R.id.btnLupa -> {
                //Limpiamos la listas que usamos para las busquedas
                listBusqueda.clear()
                listBusquedaIDLibros.clear()
                //Llamamos a la función cargaDatosBD() para que nos reinicie las listas con todos los libros de la BBDD
                cargaDatosBD()
                //Guardamos lo que ha escrito el usuario en el EditText en una variable
                val busqueda = nombreDelLibroEditText!!.text.toString()
                //Recorremos la lista que despues del cargaDatosBD() contiene todos los libros y añadimos a las listas
                // de busqueda los datos de los libros que contengan el texto escrito por el usuario en su titulo
                for (i in listTitulos){
                    if(i.contains(busqueda)){
                        listBusqueda.add(i)
                        listBusquedaIDLibros.add(i)
                    }
                }
                //Llamomos a la funcion adapter() para que rellene la ListView con las listas de busqueda
                adapter(listBusqueda, listBusquedaIDLibros)
            }
        }
    }
}

