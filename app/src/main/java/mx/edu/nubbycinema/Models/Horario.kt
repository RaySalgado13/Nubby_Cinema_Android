package mx.edu.nubbycinema.Models

import java.io.Serializable

data class Horario (val idFuncion:Int, val idSucursal:Int, val nombrePelicula:String, val dia:String, val hora:String, val sala:String, val precio:Int, val sucursal:String): Serializable{
}