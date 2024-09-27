package com.example.dbz_api.baseDeDatos

import android.content.ContentValues
import android.content.Context
import android.util.Log

class FuncBBDD {

    fun insertarEnBd (context: Context, idUsuario:String,emailUsuario:String,passwordUsuario:String){

        try {
            var conexion=SQLite(context,"login",null,1)
            var baseDeDatos= conexion.writableDatabase


            var registro = ContentValues()
            registro.put("id",idUsuario)
            registro.put("email",emailUsuario)
            registro.put("password",passwordUsuario)


            baseDeDatos.insert("usuario", null, registro)


        }catch (error: Exception){
            Log.e("DB_ERROR", "Error al insertar en la base de datos", error)
        }


    }

}