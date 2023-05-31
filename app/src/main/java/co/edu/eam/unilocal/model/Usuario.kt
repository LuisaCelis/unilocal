package co.edu.eam.unilocal.model

class Usuario() {

    var key:String = ""
    var rol: Rol = Rol.CLIENTE
    var name:String = ""
    var img:String = ""
    var nickname:String = ""
    var email:String = ""
    var password:String = ""
    var estado: EstadoUsuario = EstadoUsuario.ACEPTADO

    constructor(nombre:String, nickname:String, correo:String, password:String, rol: Rol):this(){
        this.name = nombre
        this.nickname = nickname
        this.email = correo
        this.password = password
        this.rol = rol
    }

    constructor(nombre:String, nickname:String, correo:String, rol: Rol):this(){
        this.name = nombre
        this.nickname = nickname
        this.email = correo
        this.rol = rol
    }

    override fun toString(): String {
        return "Usuario(nickname='$nickname') ${super.toString()}"
    }
}