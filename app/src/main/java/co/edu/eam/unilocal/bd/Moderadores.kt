package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.model.Moderador

object Moderadores {
    private val moderadores:ArrayList<Moderador> = ArrayList()

    init {
        moderadores.add( Moderador(1, "Moderador1", "mode1@email.com", "1234"))
        moderadores.add( Moderador(2, "Moderador2", "mode2@email.com", "1234"))
    }

    fun listar():ArrayList<Moderador>{
        return moderadores
    }

    fun obtener(id:Int): Moderador?{
        return moderadores.firstOrNull { m -> m.id == id }
    }

}