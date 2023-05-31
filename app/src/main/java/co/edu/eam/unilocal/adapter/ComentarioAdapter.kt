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
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.DetalleLugarActivity
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ItemComentarioBinding
import co.edu.eam.unilocal.databinding.ItemLugarBinding
import co.edu.eam.unilocal.fragmentos.ComentariosLugarFragment
import co.edu.eam.unilocal.model.Comentario
import co.edu.eam.unilocal.model.Lugar
import co.edu.eam.unilocal.model.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class ComentarioAdapter(var comentarios:ArrayList<Comentario>): RecyclerView.Adapter<ComentarioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ComentarioAdapter.ViewHolder, position: Int) {
        holder.bind( comentarios[position] )
    }

    override fun getItemCount() = comentarios.size

    inner class ViewHolder(var itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nickname: TextView = itemView.findViewById(R.id.usuario_nickname)
        val fecha: TextView = itemView.findViewById(R.id.fecha_comentario)
        val contenido: TextView = itemView.findViewById(R.id.contenido_comentario)
        val e1:TextView = itemView.findViewById(R.id.e_1)
        val e2:TextView = itemView.findViewById(R.id.e_2)
        val e3:TextView = itemView.findViewById(R.id.e_3)
        val e4:TextView = itemView.findViewById(R.id.e_4)
        val e5:TextView = itemView.findViewById(R.id.e_5)
        val arreglo = arrayOf(e1,e2,e3,e4,e5)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(comentario: Comentario) {
            Firebase.firestore
                .collection("usuarios")
                .document(comentario.idUser)
                .get()
                .addOnSuccessListener { u ->
                    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                    val usuario = u.toObject(Usuario::class.java)
                    nickname.text = usuario!!.nickname
                    fecha.text = simpleDateFormat.format(comentario.fecha.time)
                    contenido.text = comentario.texto
                    val rango = comentario.calificacion
                    for (i in 0..rango-1){
                        (arreglo[i] as TextView).setTextColor(ContextCompat.getColor(arreglo[i].context,R.color.yellow))
                    }
                }
        }

        override fun onClick(v: View?) {

        }
    }
}