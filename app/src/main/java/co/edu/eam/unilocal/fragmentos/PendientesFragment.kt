package co.edu.eam.unilocal.fragmentos

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.adapter.LugarAdapterMod
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentPendientesBinding
import co.edu.eam.unilocal.model.EstadoLugar
import co.edu.eam.unilocal.model.Lugar
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class PendientesFragment : Fragment() {

    lateinit var binding:FragmentPendientesBinding
    private var listaPendientes:ArrayList<Lugar> = ArrayList()
    lateinit var adapterLista:LugarAdapterMod

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("PendientesFragment", "Creando vista para fragmento de pendientes")

        binding = FragmentPendientesBinding.inflate(inflater, container, false)

        listaPendientes = Lugares.listarPorEstado(EstadoLugar.SIN_REVISAR)
        Log.d("PendientesFragment", "Lugares pendientes: ${listaPendientes.size}")

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
                val codigoLugar = listaPendientes[pos].id
                val lugar = Lugares.obtener(codigoLugar)

                when(direction){

                    ItemTouchHelper.LEFT -> {

                        Lugares.cambiarEstado( codigoLugar, EstadoLugar.ACEPTADO )
                        listaPendientes.remove(lugar)
                        adapterLista.notifyItemRemoved(pos)

                        Snackbar.make(binding.listaLugaresPendientes, "Lugar aceptado!", Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", View.OnClickListener {
                                Lugares.cambiarEstado( codigoLugar, EstadoLugar.SIN_REVISAR )
                                listaPendientes.add(pos, lugar!!)
                                adapterLista.notifyItemInserted(pos)
                            }).show()
                    }
                    ItemTouchHelper.RIGHT -> {

                        Lugares.cambiarEstado( codigoLugar, EstadoLugar.RECHAZADO )
                        listaPendientes.remove(lugar)
                        adapterLista.notifyItemRemoved(pos)

                        Snackbar.make(binding.listaLugaresPendientes, "Lugar rechazado!", Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", View.OnClickListener {
                                Lugares.cambiarEstado( codigoLugar, EstadoLugar.SIN_REVISAR )
                                listaPendientes.add(pos, lugar!!)
                                adapterLista.notifyItemInserted(pos)
                            }).show()
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


}