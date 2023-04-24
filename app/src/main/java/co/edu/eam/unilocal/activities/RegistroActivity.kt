package co.edu.eam.unilocal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityRegistroBinding
import co.edu.eam.unilocal.model.Usuario


class RegistroActivity : AppCompatActivity() {

    lateinit var binding:ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegistro.setOnClickListener{ registrarUsuario()}
    }

    fun registrarUsuario(){
        val name = binding.nameUsuario.text.toString()
        val nickname = binding.nicknameUsuario.text.toString()
        val email = binding.emailUsuario.text.toString()
        val password = binding.passwordUsuario.text.toString()

        if (name.isEmpty()){
            binding.nameLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.nameLayout.error = null
        }

        if (nickname.isEmpty()){
            binding.nicknameLayout.error = getString(R.string.es_obligatorio)
        }else{
            if(nickname.length > 10){
                binding.nicknameLayout.error = getString(R.string.maximo_caracteres)
            }else{
                binding.nicknameLayout.error = null
            }
        }

        if (email.isEmpty()){
            binding.emailLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.emailLayout.error = null
        }

        if (password.isEmpty()){
            binding.passwordLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.passwordLayout.error = null
        }

        if (name.isNotEmpty() && nickname.isNotEmpty() && nickname.length <= 10 && email.isNotEmpty() && password.isNotEmpty()){
            val usuario = Usuario(Usuarios.darID(),name,nickname,email,password)
            Usuarios.agregar(usuario)
            Toast.makeText(this,getString(R.string.datos_correctos), Toast.LENGTH_LONG).show()
            Log.e("Registro","El numero de id es: " + Usuarios.darID())
            cambiarActividad()
        }

    }

    fun cambiarActividad(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
}