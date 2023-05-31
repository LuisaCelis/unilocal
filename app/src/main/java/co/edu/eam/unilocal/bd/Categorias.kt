package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.model.Category

object Categorias {
    private val categorias:ArrayList<Category> = ArrayList()

    init {

        /*categorias.add(Category("Hotel","\uf594"))
        categorias.add(Category("Coffee shop","\uf7b6"))
        categorias.add(Category("Restaurant","\uf2e7"))
        categorias.add(Category("Park","\uf1bb"))
        categorias.add(Category("Bar","\uf0fc"))*/
    }

    fun listar(): ArrayList<Category>{
        return categorias
    }

    fun get(id:String):Category?{
        return categorias.firstOrNull { c -> c.key == id }
    }
}