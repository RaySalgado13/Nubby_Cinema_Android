package mx.edu.nubbycinema.Models

data class Boleto(val id:Int, val asiento:String?, val pelicula:String, val dia:String, val hora:String, val sala:String, val sucursal:String) {

}