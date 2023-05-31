package co.edu.eam.unilocal.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.model.Lugar
import co.edu.eam.unilocal.activities.DetalleLugarActivity
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ItemLugarBinding
import co.edu.eam.unilocal.model.Category
import co.edu.eam.unilocal.model.Comentario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.bumptech.glide.Glide

class LugarAdapter (private var lugares:ArrayList<Lugar>): RecyclerView.Adapter<LugarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemLugarBinding.inflate( LayoutInflater.from(parent.context), parent, false )
        //val v = LayoutInflater.from(parent.context).inflate( R.layout.item_lugar, parent, false )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( lugares[position] )
    }

    override fun getItemCount() = lugares.size

    inner class ViewHolder(private var view: ItemLugarBinding):RecyclerView.ViewHolder(view.root), View.OnClickListener {

        private var codigoLugar: String = ""

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar: Lugar) {
            view.nombreLugar.text = lugar.name
            view.direccionLugar.text = lugar.direccion

            val estado = lugar.estaAbierto()

            if (estado == "Abierto") {
                view.estadoLugar.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green
                    )
                )
                view.horarioLugar.text = "Cierra a las ${lugar.obtenerHoraCierre()}:00"
            } else {
                view.estadoLugar.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                view.horarioLugar.text = "Abre el ${lugar.obtenerHoraApertura()}"
            }

            val comentarios: ArrayList<Comentario> = ArrayList()
            Firebase.firestore
                .collection("Lugares")
                .document(lugar.key)
                .collection("comentarios")
                .get()
                .addOnSuccessListener { c ->
                    for (doc in c) {
                        comentarios.add(doc.toObject(Comentario::class.java))
                    }
                    val calificacion = lugar.obtenerCalificacionPromedio(comentarios)

                    for (i in 0..calificacion){
                        (view.listaEstrellas[i] as TextView).setTextColor(ContextCompat.getColor(view.listaEstrellas.context,R.color.yellow))
                    }
                }
            view.estadoLugar.text = lugar.estaAbierto()
            Glide.with(view.root.context)
                .load(lugar.imagenes[0])
                .into(view.imgLugar)
            Firebase.firestore
                .collection("categorias")
                .document(lugar.idCategory)
                .get()
                .addOnSuccessListener {
                    view.iconoLugar.text = it.toObject(Category::class.java)!!.icon
                }
            codigoLugar = lugar.key

        }

        override fun onClick(p0: View?) {
            val intent = Intent(view.root.context, DetalleLugarActivity::class.java)
            intent.putExtra("codigo", codigoLugar)
            view.root.context.startActivity(intent)
        }


    }
}