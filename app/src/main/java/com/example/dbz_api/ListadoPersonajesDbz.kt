package com.example.dbz_api // Asegúrate de que esto coincide con tu paquete

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class ListadoPersonajesDbz : AppCompatActivity() {
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_personajes_dbz)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setLogo(R.drawable.esfera)
        supportActionBar!!.title=getString(R.string.titulo_toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, ListadoPersonajesFragment())
                .commit()
        }

        toolbar.post {
            for (i in 0 until toolbar.childCount) {
                val view = toolbar.getChildAt(i)
                if (view is TextView && view.text == supportActionBar?.title) {
                    view.setOnClickListener {
                        // Redirigir a la ventana de inicio (MainActivity en este caso)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    break
                }
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_listado -> {
                val intent = Intent(this, ListadoPersonajesDbz::class.java)
                startActivity(intent)
            }
            R.id.item_cerrar_sesion -> {
                val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                preferencias.edit().clear().apply()

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


