package co.edu.eam.unilocal.model

class Category(
    var id:Int,
    var name:String,
    var icon: String) {

    override fun toString(): String {
        return "Categoria(id=$id, nombre='$name', icono='$icon')"
    }
}