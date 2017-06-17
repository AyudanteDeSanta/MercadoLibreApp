package myapp.surfer.com.mercadolibresena;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Surfer on 23/05/2017.
 */

public class ProductoAdapter extends ArrayAdapter<Producto> implements View.OnClickListener {
    Context context;
    int resource;
    Producto[] objects;
    static List<Producto> productosSeleccionados;
    boolean mostrarBotonAgregar;
    int usuarioLogueado;

    public ProductoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Producto[] objects, List<Producto> productosSeleccionados, boolean mostrarBotonAgregar, int usuarioLogueado) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.productosSeleccionados = productosSeleccionados;
        this.mostrarBotonAgregar = mostrarBotonAgregar;
        this.usuarioLogueado = usuarioLogueado;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ProductoHolder holder = null;

        if (row==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource, parent,false);

            holder = new ProductoHolder();
            holder.imagen = (ImageView) row.findViewById(R.id.ivImagenFila);
            holder.precio = (TextView) row.findViewById(R.id.tvPrecioFila);
            holder.cuotas = (TextView) row.findViewById(R.id.tvCuotasFila);
            holder.descripcion = (TextView) row.findViewById(R.id.tvDescripcionFila);
            holder.seleccionar = (ImageView) row.findViewById(R.id.ivSeleccionarFila);

            row.setTag(holder);
        }
        else{
            holder = (ProductoHolder)row.getTag();
        }

        Producto producto = objects[position];
        holder.descripcion.setText(producto.nombre);
        holder.imagen.setImageResource(producto.icon);
        holder.precio.setText(producto.precioConvertido);
        holder.cuotas.setText(producto.cuotas);
        holder.seleccionar.setOnClickListener(this);
        holder.seleccionar.setTag(position);

        holder.seleccionar.setVisibility(mostrarBotonAgregar == false ? View.INVISIBLE : View.VISIBLE);

        return row;
    }

    @Override
    public void onClick(View view)
    {
        int posicion = (int) view.getTag();
        Object object = getItem(posicion);
        Producto producto = (Producto) object;

        switch (view.getId()){
            case R.id.ivSeleccionarFila:
                productosSeleccionados.add(producto);
                //String listaProductos = obtenerListaProductos(productosSeleccionados);
                grabarArticulosFactura(producto.getId());
                Toast.makeText(context,"Total productos en el carrito: "+String.valueOf(productosSeleccionados.size()) ,Toast.LENGTH_SHORT).show();

                break;
        }
    }



    private void grabarArticulosFactura(int idProducto)
    {
        if (idProducto<=0) return;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "MercadoLibre", null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("idUsuario",usuarioLogueado);
        registro.put("idProducto",idProducto);

        db.insert("ProductosFactura",null,registro);
        db.close();
    }

    private String obtenerListaProductos(List<Producto> productos){
        String lista = "";
        for (Producto producto :
                productos) {
            if (lista.equals("")){
                lista = String.valueOf(producto.getId());
            }
            else {
                lista = lista + "," + producto.getId();
            }
        }
        Log.d("armandoLista", lista);
        return lista;
    }

    public static List<Producto> getProductosSeleccionados()
    {
        return productosSeleccionados;
    }

    static class ProductoHolder
    {
        ImageView imagen;
        TextView precio;
        TextView cuotas;
        TextView descripcion;
        ImageView seleccionar;
    }
}
