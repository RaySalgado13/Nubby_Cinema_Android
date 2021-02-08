package mx.edu.nubbycinema

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import mx.edu.nubbycinema.Models.Usuario
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = Color.rgb(205,76,76)
        window.navigationBarColor = Color.rgb(255,255,255)


        val button = btnIniciarSesion.setOnClickListener{
            /*val intent = Intent(this,Dashboard::class.java)
            startActivity(intent)*/
            checkData()
        }
    }

    private fun checkData(){
        val usuario = fieldUsuario.text
        val contrasenia = fieldPassword.text

        val url = "http://192.168.0.114/Phpfiles/encuentra_usuario.php?usuario=$usuario&clave=$contrasenia"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                //textView.text = "Response: %s".format(response.toString())
                println(response.toString())
                interpretaUsuario(response)
            },
            Response.ErrorListener { error ->
                println("HUBO UN ERROR $error")
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)

    }

    private fun interpretaUsuario(response: JSONObject?) {
        response?.let {
            val output = it.getJSONObject("output")
            if(output.length() != 0){
                println("::OUTPUT ACCESO CONCEDIDO::")
                val userJSON = output.get("0") as JSONObject
                val user = Usuario(
                    userJSON.get("id_usuario").toString().toInt(),
                    userJSON.get("usuario").toString(),
                    userJSON.get("contrasenia").toString(),
                    """${userJSON.get("nombre").toString()} ${userJSON.get("apellidos").toString()}""",
                    userJSON.get("correo").toString(),
                    userJSON.get("celular").toString()
                )

                val intent = Intent(this,Dashboard::class.java)
                intent.putExtra("usuario",user)
                startActivity(intent)
            }
            else{
                println("OUTPUT:: NO SE ENCONTRO USUARIO")

            }
        }
    }

}