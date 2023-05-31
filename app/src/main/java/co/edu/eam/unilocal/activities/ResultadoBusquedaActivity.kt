package co.edu.eam.unilocal.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityResultadoBusquedaBinding
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Lugar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ResultadoBusquedaActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultadoBusquedaBinding
    var textoBusqueda:String = ""
    lateinit var adapter:LugarAdapter
    lateinit var listaLugares:ArrayList<Lugar>
    lateinit var lugaresEncontrados: ArrayList<Lugar>
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultadoBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textoBusqueda = intent.extras!!.getString("texto", "")
        listaLugares = ArrayList()
        lugaresEncontrados = ArrayList()

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialogo_carga)
        dialog = builder.create()

        if(textoBusqueda.isNotEmpty()){
            setDialog(true)
            Firebase.firestore
                .collection("Lugares")
                .whereEqualTo("estado", EstadoLugar.ACEPTADO)
                .get()
                .addOnSuccessListener {
                    for(doc in it ){
                        val lugar = doc.toObject(Lugar::class.java)
                        lugar.key = doc.id
                        listaLugares.add(lugar)
                    }
                    lugaresEncontrados = listaLugares.filter { l -> l.name.lowercase().contains(textoBusqueda.lowercase()) }.toCollection(ArrayList())
                    adapter = LugarAdapter(lugaresEncontrados)
                    binding.listaLugares.adapter = adapter
                    binding.listaLugares.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    setDialog(false)
                }
        }
    }

    private fun setDialog(show: Boolean) {
        if (show) dialog.show() else dialog.dismiss()
    }
}