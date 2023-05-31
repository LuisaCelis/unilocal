package co.edu.eam.unilocal.bd

import android.util.Log
import co.edu.eam.unilocal.model.Rol
import co.edu.eam.unilocal.model.Usuario

object Usuarios {
    private val usuarios:ArrayList<Usuario> = ArrayList()

    init {
        usuarios.add(Usuario("Luisa","Luisa","luisa@gmail.com","12345", Rol.CLIENTE))
        usuarios.add(Usuario("Sebastian","Sebas","sebas@gmail.com","67890",Rol.MODERADOR))
        usuarios.add( Usuario("Maria", "maria", "maria@hotmail.com", "5437",Rol.MODERADOR) )
        usuarios.add( Usuario("Laura", "laura", "laura@email.com", "6543",Rol.MODERADOR) )
        usuarios.add( Usuario( "Marcos", "marcos", "marcos@email.com", "8635",Rol.CLIENTE) )
    }

    fun listar(): ArrayList<Usuario>{
        return usuarios
    }

    fun agregar(usuario: Usuario){
        usuarios.add(usuario)
        Log.e("Registro activity","usuario con id: " + usuarios.size)
    }

    fun obtener(id:String): Usuario?{
        return usuarios.firstOrNull { u -> u.key == id }
    }

    fun darID():Int {
        return usuarios.size+1
    }
}