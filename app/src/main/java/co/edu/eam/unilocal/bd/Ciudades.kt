package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.model.Ciudad

object Ciudades {
    private val ciudades: ArrayList<Ciudad> = ArrayList()

    init {
        ciudades.add(Ciudad(1, "Armenia"))
        ciudades.add(Ciudad(2, "Periera"))
        ciudades.add(Ciudad(3, "Bogota"))
        ciudades.add(Ciudad(4, "Calarca"))
        ciudades.add(Ciudad(5, "Manizales"))
    }

    fun listar(): ArrayList<Ciudad>{
        return ciudades;
    }

    fun obtener(id: Int): Ciudad?{
        return ciudades.firstOrNull{c -> c.id == id}
    }
}