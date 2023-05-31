package co.edu.eam.unilocal.model

class Posicion () {
    var lat:Double=0.0
    var lng:Double=0.0
    var key:String = ""

    constructor(lat:Double, lng:Double):this(){
        this.lat = lat
        this.lng = lng
    }

    override fun toString(): String {
        return "Posicion(lat=$lat, lng=$lng)"
    }
}