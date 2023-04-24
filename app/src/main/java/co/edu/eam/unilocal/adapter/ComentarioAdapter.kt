package co.edu.eam.unilocal.adapter

import android.content.Intent
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
import co.edu.eam.unilocal.model.Comentario
import co.edu.eam.unilocal.model.Lugar
import co.edu.eam.unilocal.model.Usuario

class ComentarioAdapter(private var comentarios:ArrayList<Comentario>): RecyclerView.Adapter<ComentarioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioAdapter.ViewHolder {
        val v = ItemComentarioBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount() = comentarios.size

    override fun onBindViewHolder(holder: ComentarioAdapter.ViewHolder, position: Int) {
        holder.bind( comentarios[position] )
    }

    inner class ViewHolder(private var view:ItemComentarioBinding):RecyclerView.ViewHolder(view.root), View.OnClickListener {

        private var codigoComentario: Int = 0

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(comentario: Comentario) {
            view.usuarioNickname.text = Usuarios.obtener(comentario.idUser)!!.name
            view.fechaComentario.text = comentario.fecha.toString()
            //view.estrellas.text = comentario.calificacion
            view.contenidoComentario.text = comentario.texto
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }
}