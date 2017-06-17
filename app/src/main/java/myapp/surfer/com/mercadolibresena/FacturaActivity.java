package myapp.surfer.com.mercadolibresena;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FacturaActivity extends BaseActivity {
    ListView lvProductosFactura;
    TextView tvTotalProductos;
    TextView tvPrecioTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        if (MainActivity.productosFactura==null)
        {
            MainActivity.productosFactura = new ArrayList<Producto>();
        }
        if (MainActivity.productosFactura.size()==0)
        {
            MainActivity.productosFactura = new ArrayList<Producto>();
        }

        tvTotalProductos = (TextView) findViewById(R.id.tvTotalProductosFactura);
        tvPrecioTotal = (TextView) findViewById(R.id.tvPrecioFactura);

        tvTotalProductos.setText(String.valueOf(MainActivity.productosFactura.size()));


        int precioTotal = precioTotal(MainActivity.productosFactura);
        tvPrecioTotal.setText(String.valueOf(precioTotal));

        Producto listaProducto[] = new Producto[MainActivity.productosFactura.size()];
        listaProducto = MainActivity.productosFactura.toArray(listaProducto);
        Log.d("Factura", String.valueOf(listaProducto.length));
        ProductoAdapter adapter = new ProductoAdapter(this, R.layout.producto_linea, listaProducto,MainActivity.productosFactura,false,0);

        lvProductosFactura = (ListView)findViewById(R.id.lvProductosFactura);
        lvProductosFactura.setAdapter(adapter);

        setTitle("Factura");
    }

    private void limpiarProductosFactura(){
        ProductoAdapter adapter = new ProductoAdapter(this, R.layout.producto_linea, new Producto[0],new ArrayList<Producto>(),false,0);
        lvProductosFactura = (ListView)findViewById(R.id.lvProductosFactura);
        lvProductosFactura.setAdapter(adapter);

        tvPrecioTotal.setText("0");
        tvTotalProductos.setText("0");
    }

    public void eliminarProductos(View v){
        SesionSharedPreferences sesion = new SesionSharedPreferences(getApplicationContext());
        int usuarioLogueado = sesion.obtenerUsuarioLogueado();

        if (usuarioLogueado==0) return;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "MercadoLibre",null,1);

        SQLiteDatabase db = admin.getWritableDatabase();

        int cantidad = db.delete("ProductosFactura","idUsuario="+usuarioLogueado,null);

        if (cantidad>0){
            limpiarProductosFactura();
            Toast.makeText(this, "Se ha quitado los productos del carrito",Toast.LENGTH_LONG).show();
        }
//        else{
//            Toast.makeText(this, "Error al momento de quitar los productos",Toast.LENGTH_LONG).show();
//        }
    }

    private int precioTotal(List<Producto> lista)
    {
        int precioTotal=0;
        for (Producto producto :
                lista) {
            precioTotal = precioTotal + producto.getPrecio();
        }

        return precioTotal;
    }


}
