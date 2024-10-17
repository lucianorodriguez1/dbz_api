package com.example.dbz_api.baseDeDatos

import android.content.ContentValues
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FuncBBDD {

    // Inserción de un usuario en la base de datos
    fun insertarEnBd(context: Context, idUsuario: String, emailUsuario: String, passwordUsuario: String) {
        // Dispatchers.IO: ejecuta la operación en un hilo secundario para operaciones de E/S
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Establecemos la conexión con la base de datos en el hilo IO
                val conexion = SQLite(context, "login", null, 1)
                val baseDeDatos = conexion.writableDatabase

                // Valores a insertar
                val registro = ContentValues().apply {
                    put("id", idUsuario)
                    put("email", emailUsuario)
                    put("password", passwordUsuario)
                }

                // Insertar datos en la base de datos
                baseDeDatos.insert("usuario", null, registro)

                // Volvemos al hilo principal para actualizar la UI (si es necesario)
                withContext(Dispatchers.Main) {
                    Log.d("DB_INSERT", "Usuario registrado con éxito")
                    // Aquí puedes actualizar la UI, como mostrar un Toast, un mensaje o cambiar la pantalla
                }

            } catch (error: Exception) {
                // Manejo de errores
                Log.e("DB_ERROR", "Error al insertar en la base de datos", error)
            }
        }
    }

    // Verificación de login
    fun verificarLogin(context: Context, idUsuario: String, passwordUsuario: String, callback: (Int) -> Unit) {
        // Dispatchers.IO: realizamos la consulta en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            val conexion = SQLite(context, "login", null, 1)
            val baseDeDatos = conexion.writableDatabase
            var valorARetornar = -1

            if (idUsuario.isNotEmpty() && passwordUsuario.isNotEmpty()) {
                // Consulta a la base de datos
                val cursor = baseDeDatos.rawQuery("SELECT id, password FROM usuario WHERE id = ?", arrayOf(idUsuario))

                if (cursor.moveToFirst()) {
                    val passwordAVerificar = cursor.getString(cursor.getColumnIndexOrThrow("password"))

                    // Verificamos si las contraseñas coinciden
                    if (passwordAVerificar == passwordUsuario) {
                        valorARetornar = 1 // Login exitoso
                    }
                }
                cursor.close() // Cerrar el cursor después de usarlo
            }

            // Volver al hilo principal para retornar el resultado y actualizar la UI
            withContext(Dispatchers.Main) {
                callback(valorARetornar)
            }
        }
    }
}
