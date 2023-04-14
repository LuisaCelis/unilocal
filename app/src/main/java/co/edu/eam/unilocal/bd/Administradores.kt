package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.model.Administrador

object Administradores {

    private val administradores:ArrayList<Administrador> = ArrayList()

    init {
        administradores.add( Administrador(1, "Admin1", "admin1@email.com", "3413"))
        administradores.add( Administrador(2, "Admin2", "admin2@email.com", "5655"))
    }

    fun listar():ArrayList<Administrador>{
        return administradores
    }

    fun login(correo:String, password:String): Administrador {
        val respuesta = administradores.first { a -> a.password == password && a.email == correo }
        return respuesta
    }
}