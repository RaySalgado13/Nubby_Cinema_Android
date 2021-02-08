package mx.edu.nubbycinema.Models

import java.io.Serializable

class Pelicula(val id: Int, val nombre: String, val duracion: Float, val categoria: String, val fecha: String, val genero: String, val director: String, val sinopsis: String) : Serializable{}
