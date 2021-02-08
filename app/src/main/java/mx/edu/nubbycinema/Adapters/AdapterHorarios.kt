package mx.edu.nubbycinema.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import mx.edu.nubbycinema.MenuConfirmarCompra
import mx.edu.nubbycinema.Models.Horario
import mx.edu.nubbycinema.Models.Usuario
import mx.edu.nubbycinema.R

class AdapterHorarios(val horarios:ArrayList<Horario>, val usuario: Int) : RecyclerView.Adapter<AdapterHorarios.initDatosViewHolder>(){
    inner class initDatosViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        val textDia: TextView
        val textHora: TextView
        val textSala: TextView
        val textSucursal: TextView
        val textPrecio: TextView
        val rowHorarioC: ConstraintLayout

        init{
            textDia = view.findViewById(R.id.dia)
            textHora = view.findViewById(R.id.hora)
            textSala = view.findViewById(R.id.sala)
            textSucursal = view.findViewById(R.id.sucursal)
            textPrecio = view.findViewById(R.id.precio)
            rowHorarioC= view.findViewById(R.id.rowHorario)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): initDatosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_horario, parent,false)

        return initDatosViewHolder(view)
    }

    override fun onBindViewHolder(holder: initDatosViewHolder, position: Int) {
        var horario = horarios[position] as Horario

        holder.textDia.text = horario.dia.toString()
        holder.textHora.text = horario.hora.toString()
        holder.textSala.text = horario.sala.toString()
        holder.textSucursal.text = horario.sucursal.toString()
        holder.textPrecio.text = horario.precio.toString()

        holder.rowHorarioC.setOnClickListener{
            Log.i("","HORARIO SELECCIONADO: ${horario.idFuncion} and ${horario.idSucursal} on ${horario.nombrePelicula} with usuario: $usuario")

            val intent = Intent(holder.view.context, MenuConfirmarCompra::class.java)
            intent.putExtra("horario",horario)
            intent.putExtra("usuario",usuario)
            holder.view.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return horarios.size
    }
}