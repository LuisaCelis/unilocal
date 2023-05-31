package co.edu.eam.unilocal.model

class Category() {

    constructor(name:String,icono:String):this(){
        this.name = name
        this.icon = icono
    }

    var key:String = ""
    var name:String = ""
    var icon: String = ""

    override fun toString(): String {
        return name
    }
}