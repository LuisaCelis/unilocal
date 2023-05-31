package co.edu.eam.unilocal.model

class Ciudad() {

    constructor(name:String):this(){
        this.name = name
    }

    var key:String = ""
    var name:String = ""

    override fun toString(): String {
        return name
    }
}