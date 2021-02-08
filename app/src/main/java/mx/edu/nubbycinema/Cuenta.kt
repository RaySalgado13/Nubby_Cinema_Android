package mx.edu.nubbycinema

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_cuenta.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.nav_view
import kotlinx.android.synthetic.main.activity_dashboard.toolbar
import mx.edu.nubbycinema.Adapters.AdapterHistorial
import mx.edu.nubbycinema.Models.Boleto
import mx.edu.nubbycinema.Models.Usuario
import org.json.JSONObject

class Cuenta : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener {

    val historial = ArrayList<Boleto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuenta)

        window.statusBarColor = Color.rgb(255,255,255)
        window.navigationBarColor = Color.rgb(205,76,76)

        setSupportActionBar(toolbar_cuenta)
        nav_view_cuenta.bringToFront()
        val toggle = ActionBarDrawerToggle(this,drawer_layout_cuenta,toolbar_cuenta,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout_cuenta.addDrawerListener(toggle)
        toggle.syncState()
        nav_view_cuenta.setNavigationItemSelectedListener(this)
        nav_view_cuenta.setCheckedItem(R.id.nav_view_cuenta)

        initDatos()

    }

    private fun initDatos() {
        val user = intent.getSerializableExtra("usuario") as Usuario
        println("""
                    USUARIO-CUENTA::
                    ${user.id}
                    ${user.usuario}
                    ${user.contrasenia}
                    ${user.nombre}
                    ${user.correo}
                    ${user.celular}
                """.trimIndent())
        textViewUsuario.text = user.usuario.toUpperCase()
        textViewNombre.text = user.nombre
        textViewCorreo.text = user.correo

        initHistorial(user)
    }

    private fun initHistorial(user:Usuario){
        val url = "http://192.168.0.114/Phpfiles/historial.php?usuario=${user.id}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                //textView.text = "Response: %s".format(response.toString())
                println(response.toString())
                interpretaHistorial(response)
            },
            Response.ErrorListener { error ->
                println("HUBO UN ERROR $error")
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)

    }

    private fun interpretaHistorial(response: JSONObject?) {
        response?.let {
            val output = it.getJSONObject("output")
            for (i in 0 until output.length()){
                val boletoJSON = output.get("$i") as JSONObject
                val boleto = Boleto(
                    boletoJSON.get("numero_boleto").toString().toInt(),
                    boletoJSON.get("asiento")?.toString(),
                    boletoJSON.get("nombre_pelicula").toString(),
                    boletoJSON.get("dia").toString(),
                    boletoJSON.get("hora_inicio").toString(),
                    boletoJSON.get("nombre_sala").toString(),
                    boletoJSON.get("nombre_sucursal").toString()
                )
                historial.add(boleto)
            }

            for(b in historial){
                println("HISTORIAL:: ${b.id} ${b.pelicula}")
            }

            recyclerHistorial.layoutManager = LinearLayoutManager(this)
            recyclerHistorial.adapter = AdapterHistorial(historial)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_cuenta -> {
                //no hace nada
            }
            R.id.item_catalogo-> {
                val user = intent.getSerializableExtra("usuario") as Usuario
                val intent = Intent(this,Dashboard::class.java)
                intent.putExtra("usuario",user)
                startActivity(intent)
            }
        }

        return false
    }
}