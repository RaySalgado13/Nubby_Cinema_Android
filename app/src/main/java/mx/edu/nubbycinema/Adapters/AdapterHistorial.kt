package mx.edu.nubbycinema.Adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_boleto.view.*
import mx.edu.nubbycinema.Models.Boleto
import mx.edu.nubbycinema.R

class AdapterHistorial(val historial: ArrayList<Boleto>) : RecyclerView.Adapter<AdapterHistorial.initDatosViewHolder>() {

    inner class initDatosViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textBoleto: TextView
        val textPelicula: TextView
        val textAsiento: TextView
        val textDia: TextView
        val textHora: TextView
        val textSala: TextView
        val textSucursal: TextView

        init {
            textBoleto = view.findViewById(R.id.boleto)
            textPelicula = view.findViewById(R.id.pelicula)
            textAsiento = view.findViewById(R.id.asiento)
            textDia = view.findViewById(R.id.dia)
            textHora = view.findViewById(R.id.hora)
            textSala = view.findViewById(R.id.sala)
            textSucursal = view.findViewById(R.id.sucursal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): initDatosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_boleto, parent,false)
        return initDatosViewHolder(view)
    }

    override fun onBindViewHolder(holder: initDatosViewHolder, position: Int) {
        var boleto = historial[position] as Boleto

        holder.textBoleto.text = boleto.id.toString()
        holder.textPelicula.text = boleto.pelicula.toString()
        holder.textAsiento.text = boleto.asiento.toString()
        holder.textDia.text = boleto.dia.toString()
        holder.textHora.text = boleto.hora.toString()
        holder.textSala.text = boleto.sala.toString()
        holder.textSucursal.text = boleto.sucursal.toString()

    }

    override fun getItemCount(): Int {
        return historial.size
    }
}