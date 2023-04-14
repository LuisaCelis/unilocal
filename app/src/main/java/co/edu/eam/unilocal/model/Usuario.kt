package co.edu.eam.unilocal.model

class Usuario(id: Int,
              name: String,
              var nickname:String,
              email: String,
              password: String): Persona(id, name, email, password) {

    override fun toString(): String {
        return "Usuario(nickname='$nickname') ${super.toString()}"
    }
}