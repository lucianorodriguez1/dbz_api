package com.example.dbz_api

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable


class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var lottieAnimationView: LottieAnimationView
    lateinit var kamehamehaButton: Button
    lateinit var buttonRegresar: Button
    lateinit var imageViewGoku: ImageView // Inicializa la variable
    lateinit var imageViewGokuKamehameha: ImageView // Nueva variable para la imagen de Goku


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setLogo(R.drawable.esfera)
        supportActionBar!!.title=getString(R.string.titulo_toolbar)

        lottieAnimationView = findViewById(R.id.lottieAnimationView) // Asegúrate de que tienes este ID en tu XML
        kamehamehaButton = findViewById(R.id.buttonKamehameha) // Asegúrate de que tienes este ID en tu XML
        buttonRegresar = findViewById(R.id.buttonRegresar)
        imageViewGoku = findViewById(R.id.imageViewGoku) // Inicializa la referencia a la imagen de Goku
        imageViewGokuKamehameha = findViewById(R.id.imageViewGokuKamehameha) // Inicializa la nueva imagen

        lottieAnimationView.visibility = View.GONE


        kamehamehaButton.setOnClickListener {
            lottieAnimationView.visibility = View.VISIBLE // Muestra la animación
            lottieAnimationView.repeatCount = LottieDrawable.INFINITE // Configura la repetición
            lottieAnimationView.playAnimation() // Inicia la animación al hacer clic
            buttonRegresar.visibility = Button.VISIBLE // Muestra el botón de regresar
            imageViewGoku.visibility = View.GONE // Oculta la imagen
            kamehamehaButton.visibility = View.GONE
            imageViewGokuKamehameha.visibility = View.VISIBLE // Muestra la imagen de Goku haciendo el Kamehameha

        }

        buttonRegresar.setOnClickListener {
            // Lógica para volver a estado normal
            lottieAnimationView.cancelAnimation() // Detiene la animación
            lottieAnimationView.visibility = View.GONE // Oculta la animación
            buttonRegresar.visibility = Button.GONE // Oculta el botón de regresar
            imageViewGoku.visibility = View.VISIBLE // Muestra nuevamente la imagen de Goku
            imageViewGokuKamehameha.visibility = View.GONE // Oculta la imagen de Goku haciendo el Kamehameha
            kamehamehaButton.visibility = View.VISIBLE
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
                // Lógica para cerrar sesión
                val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                preferencias.edit().clear().apply() // Eliminar las credenciales guardadas

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}