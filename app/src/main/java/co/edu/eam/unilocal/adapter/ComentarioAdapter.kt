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

class ComentarioAdapter(private var comentarios:ArrayList<Comentario>): RecyclerView.Adapter<ComentarioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioAdapter.ViewHolder {
        val v = ItemComentarioBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ComentarioAdapter.ViewHolder, position: Int) {
        holder.bind( comentarios[position] )
    }

    override fun getItemCount() = comentarios.size

    inner class ViewHolder(private var view:ItemComentarioBinding):RecyclerView.ViewHolder(view.root), View.OnClickListener {

        private var codigoComentario: Int = 0

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(comentario: Comentario) {
            view.usuarioNickname.text = Usuarios.obtener(comentario.idUser)!!.name
            var dat= Usuarios.obtener(comentario.idUser)!!.name
            Log.e("userNick","result:  ${dat}")
            view.fechaComentario.text = comentario.fecha.toString()
            val calificacion = comentario.calificacion
            for (i in 0..calificacion){
                (view.estrellas[i] as TextView).setTextColor(ContextCompat.getColor(view.estrellas.context,R.color.yellow))
            }
            view.contenidoComentario.text = comentario.texto
            codigoComentario = comentario.id
            Log.d("id", "idUser: ${codigoComentario}")
        }

        override fun onClick(v: View?) {

        }
    }
}