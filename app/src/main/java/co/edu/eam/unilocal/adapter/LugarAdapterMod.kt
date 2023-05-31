package co.edu.eam.unilocal.adapter

import android.content.Intent
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.model.Lugar
import co.edu.eam.unilocal.activities.DetalleLugarActivity
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.databinding.ItemLugarBinding
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.databinding.FragmentInfoLugarBinding
import co.edu.eam.unilocal.databinding.ItemModBinding
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityModeradorBinding
import co.edu.eam.unilocal.model.Category
import co.edu.eam.unilocal.model.Comentario
import co.edu.eam.unilocal.model.EstadoLugar
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class LugarAdapterMod (private var lugares:ArrayList<Lugar>): RecyclerView.Adapter<LugarAdapterMod.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val v = LayoutInflater.from(parent.context).inflate( R.layout.item_mod, parent, false )
        val v = ItemModBinding.inflate( LayoutInflater.from(parent.context), parent, false )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: LugarAdapterMod.ViewHolder, position: Int) {
        holder.bind( lugares[position] )
    }

    override fun getItemCount() = lugares.size

    inner class ViewHolder(private var view: ItemModBinding):RecyclerView.ViewHolder(view.root), View.OnClickListener {

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
                    val calificacion = obtenerCalificacionLugar(comentarios)
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

        fun obtenerCalificacionLugar(comentarios:ArrayList<Comentario>):Int{
            var promedio = 0
            if(comentarios.size >0) {
                val suma = comentarios.stream().map { c -> c.calificacion }
                    .reduce { suma, valor -> suma + valor }.get()
                promedio = suma / comentarios.size
            }
            return promedio
        }
    }

}