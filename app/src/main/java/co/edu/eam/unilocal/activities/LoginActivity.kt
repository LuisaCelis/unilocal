package co.edu.eam.unilocal.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Personas
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityLoginBinding
import co.edu.eam.unilocal.model.Administrador
import co.edu.eam.unilocal.model.Moderador
import co.edu.eam.unilocal.model.Usuario

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val correo = sp.getString("correo_usuario", "")
        val tipo = sp.getString("tipo_usuario", "")

        if(correo!!.isNotEmpty() && tipo!!.isNotEmpty()) {

            when (tipo) {
                "usuario" -> startActivity(Intent(this, MainActivity::class.java))
                "moderador" -> startActivity(Intent(this, ModeradorActivity::class.java))
                "admin" -> startActivity(Intent(this, AdministradorActivity::class.java))
            }
            finish()
        } else{
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.btnLogin.setOnClickListener{login()}
            binding.btnRegistro.setOnClickListener { irRegistro() }
        }
    }

    fun irRegistro(){
        startActivity( Intent(this, RegistroActivity::class.java) )
    }

    fun login(){
        val email = binding.emailUsuario.text
        var password = binding.passwordUsuario.text

        if (email.isEmpty()){
            binding.correoLayout.isErrorEnabled = true
            binding.correoLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.correoLayout.error = null
        }

        if (password.isEmpty()){
            binding.passwordLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.passwordLayout.error = null
        }

        if(email.isNotEmpty() && password.isNotEmpty()){
            try {
                val persona = Personas.login(email.toString(),password.toString())

                if(persona!=null){
                    val tipo = if( persona is Usuario) "usuario" else if( persona is Moderador) "moderador" else "admin"

                    val sharedPreferences = this.getSharedPreferences( "sesion", Context.MODE_PRIVATE ).edit()
                    sharedPreferences.putInt("codigo_usuario", persona.id)
                    sharedPreferences.putString("correo_usuario", persona.email)
                    sharedPreferences.putString("tipo_usuario", tipo)

                    sharedPreferences.commit()

                    when(persona){
                        is Usuario -> startActivity( Intent(this, MainActivity::class.java) )
                        is Moderador -> startActivity( Intent(this, ModeradorActivity::class.java) )
                        is Administrador -> startActivity( Intent(this, AdministradorActivity::class.java) )
                    }
                    binding.emailUsuario.setText("")
                    binding.passwordUsuario.setText("")
                } else{
                    Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
                }

            }catch (e:Exception){
                Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
            }
        }
    }
}