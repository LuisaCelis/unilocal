package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.model.Ciudad

object Ciudades {
    private val ciudades: ArrayList<Ciudad> = ArrayList()

    init {
        //ciudades.add(Ciudad("Armenia"))
        //ciudades.add(Ciudad("Pereira"))
        //ciudades.add(Ciudad("Bogota"))
        //ciudades.add(Ciudad("Calarca"))
        //ciudades.add(Ciudad("Manizales"))
    }

    fun listar(): ArrayList<Ciudad>{
        return ciudades;
    }
}