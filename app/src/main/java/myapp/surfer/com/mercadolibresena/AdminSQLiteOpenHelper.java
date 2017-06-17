package myapp.surfer.com.mercadolibresena;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Surfer on 8/06/2017.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Usuarios(idUsuario integer primary key, nombre text, correo text, pass text)");

        db.execSQL("create table ProductosFactura(idProducto integer, idUsuario integer)");

        CrearUsuarios(db);
    }

    private void CrearUsuarios(SQLiteDatabase db)
    {
        ContentValues registro = new ContentValues();

        registro.put("idUsuario","1");
        registro.put("nombre","Didier");
        registro.put("correo","didier1988@gmail.com");
        registro.put("pass","1234567");

        db.insert("Usuarios",null,registro);

        registro = new ContentValues();
        registro.put("idUsuario","2");
        registro.put("nombre","Juan");
        registro.put("correo","juan@gmail.com");
        registro.put("pass","1234567");

        db.insert("Usuarios",null,registro);

        registro = new ContentValues();
        registro.put("idUsuario","3");
        registro.put("nombre","Sena");
        registro.put("correo","Sena@gmail.com");
        registro.put("pass","123");

        db.insert("Usuarios",null,registro);

        //db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Usuarios");
        db.execSQL("create table Usuarios(idUsuario integer primary key, nombre text, correo text, pass text)");

        db.execSQL("drop table if exists ProductosFactura");
        db.execSQL("create table ProductosFactura(idProducto integer, idUsuario integer)");


    }
}
