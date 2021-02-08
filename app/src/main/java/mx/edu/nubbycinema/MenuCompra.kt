package mx.edu.nubbycinema

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cuenta.*
import kotlinx.android.synthetic.main.activity_menu_compra.*
import kotlinx.android.synthetic.main.row_boleto.*
import mx.edu.nubbycinema.Adapters.AdapterHistorial
import mx.edu.nubbycinema.Adapters.AdapterHorarios
import mx.edu.nubbycinema.Models.Horario
import mx.edu.nubbycinema.Models.Pelicula
import mx.edu.nubbycinema.Models.Usuario
import org.json.JSONObject

class MenuCompra : AppCompatActivity() {
    private val url = "http://192.168.0.114/Phpfiles/horarios.php?pelicula="
    private val horarios = ArrayList<Horario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_compra)

        window.statusBarColor = Color.rgb(205,76,76)
        window.navigationBarColor = Color.rgb(205,76,76)

        val pelicula = intent.getSerializableExtra("pelicula") as Pelicula
        val pathBanner = "http://192.168.56.1:80/Images/BannerPeliculas/${pelicula.nombre}1.png"
        val banner = findViewById<ImageView>(R.id.imgBanner)

        titulo.text = pelicula.nombre
        genero.text = pelicula.genero
        duracion.text = "Duracion (min): "+pelicula.duracion.toString()
        sinopsis.text = pelicula.sinopsis
        Picasso.get().load(pathBanner).into(banner)

        initHorarios(pelicula)
    }

    private fun initHorarios(pelicula: Pelicula){
        val urlHorarios = url + pelicula.id.toString()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, urlHorarios, null,
            Response.Listener { response ->
                //textView.text = "Response: %s".format(response.toString())
                println(response.toString())
                interpretaHorarios(response)
            },
            Response.ErrorListener { error ->
                println("HUBO UN ERROR $error")
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)

    }

    private fun interpretaHorarios(response: JSONObject?) {
        response?.let {
            val output = it.getJSONObject("output")
            for (i in 0 until output.length()){
                val horarioJSON = output.get("$i") as JSONObject
                val horario = Horario(
                    horarioJSON.get("id_funcion").toString().toInt(),
                    horarioJSON.get("id_sucursal").toString().toInt(),
                    horarioJSON.get("nombre_pelicula").toString(),
                    horarioJSON.get("dia").toString(),
                    horarioJSON.get("hora_inicio").toString(),
                    horarioJSON.get("nombre_sala").toString(),
                    horarioJSON.get("precio").toString().toInt(),
                    horarioJSON.get("nombre_sucursal").toString()
                )
                horarios.add(horario)
            }

            for (h in horarios){
                println("HORARIOS:: ${h.idFuncion} ${h.idSucursal} for ${h.nombrePelicula}")
            }

            val idUsuario = intent.getSerializableExtra("usuario") as Int

            recyclerHorarios.layoutManager = LinearLayoutManager(this)
            recyclerHorarios.adapter = AdapterHorarios(horarios,idUsuario)


        }
    }
}