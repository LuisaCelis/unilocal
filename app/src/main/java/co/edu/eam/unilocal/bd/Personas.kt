package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.model.Persona

object Personas {
    fun login(correo:String, password:String): Persona?{

        var respuesta:Persona?
        respuesta = Usuarios.listar().firstOrNull{u -> u.password == password && u.email == correo}

        if(respuesta == null){
            respuesta = Moderadores.listar().firstOrNull{ u -> u.password == password && u.email == correo }

            if(respuesta == null) {
                respuesta = Administradores.listar().firstOrNull{ u -> u.password == password && u.email == correo }
            }
        }

        return respuesta
    }
}