package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.model.Comentario

object Comentarios {
    private val comentarios: ArrayList<Comentario> = ArrayList()

    //init {
    //    comentarios.add(Comentario(1, "Excelente servicio y buena ambiente", 1, 2, 5))
    //    comentarios.add(Comentario(2, "Muy demorado, no volvere", 4, 1, 1))
    //    comentarios.add(Comentario(3, "Buena comida mexicana, precios razonables", 3, 3, 4))
    //    comentarios.add(Comentario(4, "El lugar es bonito pero muy lejos", 2, 4, 3))
    //    comentarios.add(Comentario(5, "no volveria, los precios son muy altos", 1, 5, 2))
    //    comentarios.add( Comentario(6, "Un hotel bien ubicado y con desayuno incluído. Recomendado.", 1, 5, 4 ) )
    //}

    fun crear(comentario: Comentario) {
        comentarios.add(comentario)
    }
}