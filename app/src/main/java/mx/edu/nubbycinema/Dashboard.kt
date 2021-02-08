package mx.edu.nubbycinema

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dashboard.*
import mx.edu.nubbycinema.Adapters.AdapterPortada
import mx.edu.nubbycinema.Models.Pelicula
import mx.edu.nubbycinema.Models.Usuario
import org.json.JSONObject

class Dashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private val moviesUrl = "http://192.168.56.1:80/Phpfiles/get_cartelera.php"
    val peliculas = ArrayList<Pelicula>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        window.statusBarColor = Color.rgb(255,255,255)
        window.navigationBarColor = Color.rgb(255,255,255)

        setSupportActionBar(toolbar)
        nav_view.bringToFront()
        val toggle = ActionBarDrawerToggle(this,drawer_layout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.nav_view)

        initCartelera()

        val user = intent.getSerializableExtra("usuario") as Usuario
        println("""
                    USUARIO-DASHBOARD::
                    ${user.id}
                    ${user.usuario}
                    ${user.contrasenia}
                    ${user.nombre}
                    ${user.correo}
                    ${user.celular}
                """.trimIndent())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_cuenta -> {
                val user = intent.getSerializableExtra("usuario") as Usuario
                val intent = Intent(this,Cuenta::class.java)
                intent.putExtra("usuario",user)
                startActivity(intent)
            }
            R.id.item_catalogo-> {
                //No hace nada
            }
        }

        return false
    }

    fun initCartelera(){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, moviesUrl, null,
            Response.Listener { response ->
                //textView.text = "Response: %s".format(response.toString())
                println(response.toString())
                interpretaPeliculas(response)
            },
            Response.ErrorListener { error ->
                println("HUBO UN ERROR $error")
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    private fun interpretaPeliculas(response: JSONObject?) {
        response?.let {
            val output = it.getJSONObject("output")
            for (i in 0 until output.length()){
                val peliculaJSON = output.get("$i") as JSONObject
                val pelicula = Pelicula(
                    peliculaJSON.get("id_pelicula").toString().toInt(),
                    peliculaJSON.get("nombre_pelicula").toString(),
                    peliculaJSON.get("duracion_minutos").toString().toFloat(),
                    peliculaJSON.get("categoria").toString(),
                    peliculaJSON.get("fecha_estreno").toString(),
                    peliculaJSON.get("genero").toString(),
                    peliculaJSON.get("director").toString(),
                    peliculaJSON.get("Sinopsis").toString()
                )
                peliculas.add(pelicula)
            }

            for (p in peliculas){
                println("Pelicula: ${p.nombre}")
            }

            val user = intent.getSerializableExtra("usuario") as Usuario
            val idUsuario = user.id
            gridCartelera.adapter = AdapterPortada(this,R.layout.panel_movies,peliculas, idUsuario)

        }
    }
}