package mx.edu.nubbycinema.Models

import java.io.Serializable

class Usuario(val id:Int, val usuario:String, val contrasenia:String, val nombre:String, val correo:String, val celular:String) : Serializable {
}