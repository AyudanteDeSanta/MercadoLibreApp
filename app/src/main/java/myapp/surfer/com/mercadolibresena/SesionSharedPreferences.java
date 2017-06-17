package myapp.surfer.com.mercadolibresena;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Surfer on 11/06/2017.
 */

public class SesionSharedPreferences {
    Context context;

    public SesionSharedPreferences(Context context){
        this.context = context;
    }

    public void guardarUsuarioLogueado(int idUsuario, String nombreUsuario, String correoUsuario){
        SharedPreferences preferencias = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("usuarioLogueado", idUsuario);
        editor.putString("nombreUsuarioLogueado", nombreUsuario);
        editor.putString("correoUsuarioLogueado", correoUsuario);

        editor.commit();
    }

    public int obtenerUsuarioLogueado(){
        SharedPreferences preferencias = context.getSharedPreferences("usuario",Context.MODE_PRIVATE);

        return preferencias.getInt("usuarioLogueado",0);
    }

    public String obtenerNombreLogueado(){
        SharedPreferences preferencias = context.getSharedPreferences("usuario",Context.MODE_PRIVATE);

        return preferencias.getString("nombreUsuarioLogueado","");
    }

    public String obtenerCorreoLogueado(){
        SharedPreferences preferencias = context.getSharedPreferences("usuario",Context.MODE_PRIVATE);

        return preferencias.getString("correoUsuarioLogueado","");
    }

}
