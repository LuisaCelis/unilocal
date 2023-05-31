package co.edu.eam.unilocal.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.CategoriasFiltroActivity
import co.edu.eam.unilocal.model.Category

class CategoriaAdapter  (var categorias:ArrayList<Category>, var context: Context): RecyclerView.Adapter<CategoriaAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_categoria, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoriaAdapter.ViewHolder, position: Int) {
        holder.bind(categorias[position])
    }

    override fun getItemCount() = categorias.size

    inner class ViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val iconoCategoria: TextView = itemView.findViewById(R.id.icono_categoria)
        var codigoCategoria:String = ""

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(categoria: Category) {
            codigoCategoria = categoria.key
            iconoCategoria.text = categoria.icon
        }

        override fun onClick(p0: View?) {
            val intent = Intent( iconoCategoria.context, CategoriasFiltroActivity::class.java )
            intent.putExtra("idCategory", codigoCategoria)
            (context as CategoriasFiltroActivity).finish()
            iconoCategoria.context.startActivity(intent)
        }
    }
}