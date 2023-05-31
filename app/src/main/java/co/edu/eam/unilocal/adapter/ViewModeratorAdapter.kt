package co.edu.eam.unilocal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.unilocal.fragmentos.AceptadosFragment
import co.edu.eam.unilocal.fragmentos.PendientesFragment
import co.edu.eam.unilocal.fragmentos.RechazadosFragment

class ViewModeratorAdapter(var fragment: FragmentActivity): FragmentStateAdapter(fragment){

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PendientesFragment()
            1 -> AceptadosFragment()
            else -> RechazadosFragment()
        }
    }
}
