package co.edu.eam.unilocal.model

class Administrador (
    id: Int,
    name: String,
    email: String,
    password: String): Persona(id, name, email, password){

    override fun toString(): String {
        return "Administrador() ${super.toString()}"
    }
}