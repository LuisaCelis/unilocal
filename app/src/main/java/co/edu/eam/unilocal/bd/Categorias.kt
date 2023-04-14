package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.model.Category

object Categorias {
    private val categorias:ArrayList<Category> = ArrayList()

    init {
        categorias.add(Category(1,"Hotel","\uf594"))
        categorias.add(Category(2,"Coffee shop","\uf7b6"))
        categorias.add(Category(3,"Restaurant","\uf2e7"))
        categorias.add(Category(4,"Park","\uf1bb"))
        categorias.add(Category(5,"Bar","\uf0fc"))
    }

    fun listar(): ArrayList<Category>{
        return categorias
    }

    fun get(id:Int):Category?{
        return categorias.firstOrNull { c -> c.id == id }
    }
}