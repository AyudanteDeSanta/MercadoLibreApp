package myapp.surfer.com.mercadolibresena;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText etUsuario, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SesionSharedPreferences sesion = new SesionSharedPreferences(getApplicationContext());

        //redireccionar a categorias si ya se había logueado previamente
        int idUsuarioLogueado = sesion.obtenerUsuarioLogueado();
        if (idUsuarioLogueado>0){
            Intent intent = new Intent(LoginActivity.this, CategoriaActivity.class);
            startActivity(intent);
            return;
        }

        setContentView(R.layout.activity_login);

        etUsuario = (EditText) findViewById(R.id.et_login_usuario);
        etPass = (EditText) findViewById(R.id.et_login_pass);
    }

    private ArrayList<String> obtenerUsuarioSesion(String usuario, String pass){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"MercadoLibre",null,1);

        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor fila = db.rawQuery("select idUsuario,nombre,correo from Usuarios where correo='"+usuario+"' and pass='"+pass+"'",null);

        ArrayList<String> usuarioEncontrado = new ArrayList<String>();
        if (fila.moveToFirst()){
            usuarioEncontrado.add(String.valueOf(fila.getInt(fila.getColumnIndex("idUsuario"))));
            usuarioEncontrado.add(fila.getString(fila.getColumnIndex("nombre")));
            usuarioEncontrado.add(fila.getString(fila.getColumnIndex("correo")));
            //return fila.getInt(fila.getColumnIndex("idUsuario"));

//            Log.d("idLogueado", String.valueOf(fila.getInt(fila.getColumnIndex("idUsuario"))));
//            Log.d("nombreLogueado", fila.getString(fila.getColumnIndex("nombre")));
//            Log.d("correoLogueado", fila.getString(fila.getColumnIndex("correo")));
        }

        return usuarioEncontrado;
    }

    public void iniciarSesion(View v){
        String usuario = etUsuario.getText().toString();

        if (usuario.equals("")){
            Toast.makeText(this, "Se debe ingresar nombre usuario ó email", Toast.LENGTH_SHORT).show();
            return;
        }

        String pass = etPass.getText().toString();

        if (pass.equals("")){
            Toast.makeText(this, "Se debe ingresar la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> usuarioSesion = obtenerUsuarioSesion(usuario,pass);

        if (usuarioSesion.size()==0){
            Toast.makeText(this, "Nombre de usuario y/o contraseña no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        SesionSharedPreferences sesion = new SesionSharedPreferences(getApplicationContext());
        //Guardar usuario logueado para recordarlo cuando ingrese y no se loguee nuevamente
        sesion.guardarUsuarioLogueado(Integer.valueOf(usuarioSesion.get(0)),usuarioSesion.get(1),usuarioSesion.get(2));



        Intent intent = new Intent(LoginActivity.this, CategoriaActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}
