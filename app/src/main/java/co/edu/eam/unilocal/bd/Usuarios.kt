package co.edu.eam.unilocal.bd

import android.util.Log
import co.edu.eam.unilocal.model.Usuario

object Usuarios {
    private val usuarios:ArrayList<Usuario> = ArrayList()

    init {
        usuarios.add(Usuario(1, "Luisa","Luisa","luisa@gmail.com","12345"))
        usuarios.add(Usuario(2, "Sebastian","Sebas","sebas@gmail.com","67890"))
        usuarios.add( Usuario(3, "Maria", "maria", "maria@hotmail.com", "5437") )
        usuarios.add( Usuario(4, "Laura", "laura", "laura@email.com", "6543") )
        usuarios.add( Usuario(5, "Marcos", "marcos", "marcos@email.com", "8635") )
    }

    fun listar(): ArrayList<Usuario>{
        return usuarios
    }

    fun agregar(usuario: Usuario){
        usuarios.add(usuario)
        Log.e("Registro activity","usuario con id: " + usuarios.size)
    }

    fun obtener(id:Int): Usuario?{
        return usuarios.firstOrNull { u -> u.id == id }
    }

    fun darID():Int {
        return usuarios.size+1
    }
}