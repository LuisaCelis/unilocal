package co.edu.eam.unilocal.fragmentos

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.activities.ModeradorActivity
import co.edu.eam.unilocal.adapter.LugarAdapterMod
import co.edu.eam.unilocal.databinding.FragmentPendientesBinding
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Lugar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class PendientesFragment() : Fragment() {

    lateinit var binding:FragmentPendientesBinding
    lateinit var listaPendientes:ArrayList<Lugar>
    lateinit var adapterLista:LugarAdapterMod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listaPendientes = ArrayList()
        binding = FragmentPendientesBinding.inflate(inflater, container, false)
        adapterLista = LugarAdapterMod(listaPendientes)
        binding.listaLugaresPendientes.adapter = adapterLista
        binding.listaLugaresPendientes.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        crearEventoSwipe()
        return binding.root
    }

    fun crearEventoSwipe(){

        val simpleCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var pos = viewHolder.adapterPosition
                val lugar = listaPendientes[pos]

                when(direction){

                    ItemTouchHelper.LEFT -> {
                        aceptarLugar(lugar!!.key, pos)
                        listaPendientes.remove(lugar)
                        adapterLista.notifyItemRemoved(pos)
                    }
                    ItemTouchHelper.RIGHT -> {
                        denegarLugar(lugar!!.key, pos)
                        listaPendientes.remove(lugar)
                        adapterLista.notifyItemRemoved(pos)
                    }

                }

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(),R.color.red ))
                    .addSwipeLeftLabel("Aceptar")
                    .addSwipeRightLabel("Rechazar")
                    .create()
                    .decorate()


            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.listaLugaresPendientes)

    }

    fun deshacerAccion(key:String,pos:Int){
        Firebase.firestore
            .collection("Lugares")
            .document(key)
            .get()
            .addOnSuccessListener {
                val lugarNuevo = it.toObject(Lugar::class.java)
                lugarNuevo!!.key = it.id
                lugarNuevo!!.idModerador = ""
                lugarNuevo!!.estado = EstadoLugar.SIN_REVISAR
                Firebase.firestore
                    .collection("lugares")
                    .document(key)
                    .set(lugarNuevo)
                    .addOnSuccessListener {
                        Snackbar.make(binding.listaLugaresPendientes, getString(R.string.lugar_no_revisado), Snackbar.LENGTH_LONG)
                        listaPendientes.add(pos, lugarNuevo!!)
                        adapterLista.notifyItemInserted(pos)

                    }
            }
    }

    fun aceptarLugar(key:String,pos:Int){
        val usuario = FirebaseAuth.getInstance().currentUser
        Firebase.firestore
            .collection("Lugares")
            .document(key)
            .get()
            .addOnSuccessListener {
                val lugarNuevo = it.toObject(Lugar::class.java)
                lugarNuevo!!.key = it.id
                lugarNuevo!!.idModerador = usuario!!.uid
                lugarNuevo!!.estado = EstadoLugar.ACEPTADO
                Firebase.firestore
                    .collection("Lugares")
                    .document(key)
                    .set(lugarNuevo)
                    .addOnSuccessListener {
                        Snackbar.make(binding.listaLugaresPendientes, getString(R.string.lugar_aceptado), Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", View.OnClickListener {
                                deshacerAccion(key, pos)
                            }).show()
                    }
            }
    }

    fun cambiarEstadoAceptado(lugar:Lugar, context: Context){
        Firebase.firestore
            .collection("lugares")
            .document(lugar.key)
            .get()
            .addOnSuccessListener {
                val lugarNuevo = it.toObject(Lugar::class.java)
                lugarNuevo!!.key = it.id
                lugarNuevo!!.idModerador = lugar.idModerador
                lugarNuevo!!.estado = EstadoLugar.ACEPTADO
                Firebase.firestore
                    .collection("lugares")
                    .document(lugar.key)
                    .set(lugarNuevo)
                    .addOnSuccessListener {
                        Toast.makeText(context, context.getString(R.string.lugar_aceptado), Toast.LENGTH_LONG).show()
                        context.startActivity(Intent(context, ModeradorActivity::class.java))
                    }
            }
    }

    fun denegarLugar(key:String,pos:Int){
        val usuario = FirebaseAuth.getInstance().currentUser
        Firebase.firestore
            .collection("lugares")
            .document(key)
            .get()
            .addOnSuccessListener {
                val lugarNuevo = it.toObject(Lugar::class.java)
                lugarNuevo!!.key = it.id
                lugarNuevo!!.idModerador = usuario!!.uid
                lugarNuevo!!.estado = EstadoLugar.RECHAZADO
                Firebase.firestore
                    .collection("lugares")
                    .document(key)
                    .set(lugarNuevo)
                    .addOnSuccessListener {
                        Snackbar.make(
                            binding.listaLugaresPendientes,
                            getString(R.string.lugar_rechazado),
                            Snackbar.LENGTH_LONG
                        )
                            .setAction(getString(R.string.deshacer), View.OnClickListener {
                                deshacerAccion(key, pos)
                            }).show()
                    }
            }
    }

    fun cambiarEstadoRechazado(lugar:Lugar, context:Context){
        Firebase.firestore
            .collection("lugares")
            .document(lugar.key)
            .get()
            .addOnSuccessListener {
                val lugarNuevo = it.toObject(Lugar::class.java)
                lugarNuevo!!.key = it.id
                lugarNuevo!!.idModerador = lugar.idModerador
                lugarNuevo!!.estado = EstadoLugar.RECHAZADO
                Firebase.firestore
                    .collection("lugares")
                    .document(lugar.key)
                    .set(lugarNuevo)
                    .addOnSuccessListener {
                        Toast.makeText(context, context.getString(R.string.lugar_rechazado), Toast.LENGTH_LONG).show()
                        context.startActivity(Intent(context, ModeradorActivity::class.java))
                    }
            }
    }

    override fun onResume() {
        super.onResume()
        listaPendientes.clear()
        Firebase.firestore
            .collection("Lugares")
            .whereEqualTo("estado", EstadoLugar.SIN_REVISAR)
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    val lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    listaPendientes.add(lugar)
                }
                adapterLista.notifyDataSetChanged()
            }
    }
}