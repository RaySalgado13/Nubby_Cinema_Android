package mx.edu.nubbycinema

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_menu_confirmar_compra.*
import mx.edu.nubbycinema.Models.Horario
import mx.edu.nubbycinema.Models.Usuario

class MenuConfirmarCompra : AppCompatActivity() {
    val values = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_confirmar_compra)

        window.statusBarColor = Color.rgb(255,255,255)
        window.navigationBarColor = Color.rgb(255,255,255)

        val horario = intent.getSerializableExtra("horario") as Horario
        val idUsuario = intent.getSerializableExtra("usuario") as Int
        initDatos(horario);

        for(i in 0..5){
            values.add((i+1).toString())
        }
        spinnerBoletos.adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,values)

        btnConfirmarCompra.setOnClickListener(){
            val cant = spinnerBoletos.selectedItemPosition + 1
            for (i in 0 until cant){
                Log.i("Boleto comprado ",(i+1).toString())
                comprarBoleto(horario,idUsuario)
            }

        }


    }

    private fun initDatos(horario: Horario) {
        dia.text = horario.dia
        hora.text = horario.hora
        sala.text = horario.sala
        sucursal.text = horario.sucursal
        precio.text = horario.precio.toString()
        val url = "http://192.168.56.1:80/Images/Portadas Peliculas/${horario.nombrePelicula}.png"
        Picasso.get().load(url).into(portadaConfirmar)

    }

    private fun comprarBoleto(horario: Horario, idUsuario: Int){
        val url = "http://192.168.0.114/Phpfiles/comprar_boleto.php?funcion=${horario.idFuncion}&sucursal=${horario.idSucursal}&usuario=$idUsuario"


        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.i("status de la compra: ","EXITOSA")
                Toast.makeText(this,"COMPRA REALIZADA CON EXITO",Toast.LENGTH_SHORT)
            },
            Response.ErrorListener { error ->
                Log.i("ERROR",error.printStackTrace().toString())
            })

        Volley.newRequestQueue(this).add(stringRequest)
    }

}