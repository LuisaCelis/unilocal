package co.edu.eam.unilocal.model

open class Persona(
    var id:Int,
    var name:String,
    var email:String,
    var password:String) {

    override fun toString(): String {
        return "Persona(id=$id, nombre='$name', correo='$email', password='$password')"
    }
}