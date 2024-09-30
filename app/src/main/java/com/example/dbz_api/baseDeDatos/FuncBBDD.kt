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

    fun verificarLogin (context:Context, idUsuario: String,passwordUsuario: String): Int{

        var conexion= SQLite(context,"login",null,1)
        var baseDeDatos = conexion.writableDatabase

        var valorARetornar = -1

        if(idUsuario.isNotEmpty() && passwordUsuario.isNotEmpty()){

            println("===============ID A VERIFICAR==================== --> $idUsuario")

            val cursor=baseDeDatos.rawQuery("select id, password from usuario where id = ?", arrayOf(idUsuario))
            println("===============Llegue 001==================== --> ")

            if(cursor.moveToFirst()){
                println("===============Llegue 002==================== --> ")
                val usuarioAVerificar = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                val passwordAVerificar = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                println("===============LA CONTRA  ES==================== --> " + passwordAVerificar)

                if(passwordAVerificar == passwordUsuario){

                    valorARetornar = 1
                }
            }
        }

        return valorARetornar

    }



}