package mx.edu.nubbycinema.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.system.Os.bind
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.panel_movies.view.*
import mx.edu.nubbycinema.Dashboard
import mx.edu.nubbycinema.MenuCompra
import mx.edu.nubbycinema.Models.Pelicula
import mx.edu.nubbycinema.Models.Usuario
import mx.edu.nubbycinema.R

class AdapterPortada(val ctx: Context, val res: Int, val peliculas: ArrayList<Pelicula>, val idUsuario: Int) : BaseAdapter() {
    override fun getCount(): Int {
        return peliculas.size
    }

    override fun getItem(position: Int): Any {
        return peliculas[position]
    }

    override fun getItemId(position: Int): Long {
        return -1
    }

    override fun getView(position: Int, view: View?, group: ViewGroup?): View {
        if(view == null){
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val vista = inflater.inflate(res, group,false)

            bind(vista,position)
            return vista
        }
        else{
            return view
        }
    }

    private fun bind(view: View, position: Int){
        val nombre = peliculas[position].nombre
        val portada = view.findViewById<ImageView>(R.id.panel_portada)

        val url = "http://192.168.56.1:80/Images/Portadas Peliculas/$nombre.png"
        Picasso.get().load(url).into(portada)

        portada.setOnClickListener{
            println("Pelicula seleccionada::$nombre")
            val intent = Intent(view.context, MenuCompra::class.java)
            intent.putExtra("pelicula",peliculas[position])
            intent.putExtra("usuario",idUsuario)
            view.context.startActivity(intent)
        }

    }
}