package myapp.surfer.com.mercadolibresena;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle toggle;
    TextView tvUsuario, tvCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        SesionSharedPreferences sesion = new SesionSharedPreferences(getApplicationContext());
        int usuarioLogueado = sesion.obtenerUsuarioLogueado();
        if (usuarioLogueado>0) {
            obtenerProductosFactura(usuarioLogueado);
        }

    }

    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);

        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, fullView, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        fullView.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        SesionSharedPreferences sesion = new SesionSharedPreferences(getApplicationContext());
        String nombreLogueado = sesion.obtenerNombreLogueado();
        String correoLogueado = sesion.obtenerCorreoLogueado();

        View hView =  navigationView.getHeaderView(0);
        tvCorreo = (TextView)hView.findViewById(R.id.tvCorreoMenu);
        tvUsuario = (TextView)hView.findViewById(R.id.tvNombreUsuarioMenu);
        tvUsuario.setText(nombreLogueado);
        tvCorreo.setText(correoLogueado);

    }

    private void obtenerProductosFactura(int idUsuario){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "MercadoLibre", null, 1);

        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select idProducto from ProductosFactura where idUsuario="+idUsuario,null);
        ArrayList<Integer> consecutivos = new ArrayList<Integer>();
        Log.d("ArrayList", "ArrayList");
        if (fila.moveToFirst()){
            fila.moveToFirst();
            //recorremos el select
            while (!fila.isAfterLast()){
                consecutivos.add(fila.getInt(fila.getColumnIndex("idProducto")));
                fila.moveToNext();
            }
            //fila.close();

        }
        db.close();

        List<Producto> listaProductos = new ArrayList<Producto>();
        Producto[] totalProductos = CrearProductos();

        for (Integer id : consecutivos) {
            for (Producto producto : totalProductos) {
                if (id.equals(producto.getId())){
                    listaProductos.add(producto);
                }
            }
        }

        MainActivity.productosFactura = listaProductos;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.opciones_basicas,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.opcionAcerca:
                AlertDialog alertDialog;

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    alertDialog = new AlertDialog.Builder(BaseActivity.this,R.style.ThemeOverlay_AppCompat_Dialog_Alert).create();
                }
                else{
                    alertDialog = new AlertDialog.Builder(BaseActivity.this).create();
                }

                alertDialog.setTitle("Acerca de");
                alertDialog.setMessage("Desarrollado por: \n ==> Didier Albarrán <==");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                dialog.dismiss();
                            }
                        });

                alertDialog.show();
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categoria) {
            Intent intent = new Intent(BaseActivity.this, CategoriaActivity.class);
            if (intent.resolveActivity(getPackageManager()) != null) {
                setTitle("Categoría");
                intent.putExtra("ACTIVITY_NAME",String.valueOf(id));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.nav_factura) {
            Intent intent = new Intent(BaseActivity.this, FacturaActivity.class);
            if (intent.resolveActivity(getPackageManager()) != null) {
                intent.putExtra("ACTIVITY_NAME",String.valueOf(id));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.nav_cerrar_sesion) {
            SesionSharedPreferences sesion = new SesionSharedPreferences(getApplicationContext());
            sesion.guardarUsuarioLogueado(0,"","");
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show();
        }



        DrawerLayout drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    public static Producto[] CrearProductos()
    {
        Producto listaProducto[] = new Producto[0];

        listaProducto = new Producto[]{
                new Producto(1,"Pc Gamer Amd A8 7600 8gb 2tb Radeon R7 Combo Monitor 21.5","$ 1.479.900",1479900,"12 cuotas de $ 123.325",R.drawable.computador1,new Categoria(1,"Computadores")),
                new Producto(2,"Equipo Escritorio Celeron 3900 2.8ghz 4gb 1tb Monitor Lg 22 ","$ 929.900",929900,"36 cuotas de $ 25.831",R.drawable.computador2,new Categoria(1,"Computadores")),
                new Producto(3,"Computador Core I5 De 7a 3,0 Ghz Con Monitor 22 / 8gb","$ 1.999.900",1999900,"36 cuotas de $ 55.553",R.drawable.computador3,new Categoria(1,"Computadores")),
                new Producto(4,"Computador De Escritorio Intel Séptima Generación I3 7100","$ 1.420.000",1420000,"36 cuotas de $ 39.444",R.drawable.computador4,new Categoria(1,"Computadores")),
                new Producto(5,"Pc Gamer Titan V2: Intel Core I5, Gtx 1060 3gb, 8gb Ram, 1tb","$ 2.699.000",2699000,"36 cuotas de $ 74.972",R.drawable.computador5,new Categoria(1,"Computadores")),
                new Producto(6,"Portátil Hp Amd A6 Quad Core 4gb 500gb Dvd Gamer Windows 10", "$ 1.299.900", 1299900, "36 cuotas de $ 22.219", R.drawable.portatil1, new Categoria(4,"Portatil")),
                new Producto(7,"Portatil Lenovo 110 Celeron 4gb Ram 500gb 14 Win10 + Morral", "$ 929.900", 929900, "36 cuotas de $ 22.219", R.drawable.portatil2, new Categoria(4,"Portatil")),
                new Producto(8,"Dell Core I7 16g Ram 2 Tera 4gb Video 17 Pulgada Touchscreen", "$ 1.999.900", 1999900, "12 cuotas de $ 274.992", R.drawable.portatil3, new Categoria(4,"Portatil")),
                new Producto(9,"Dell Core I7 16g Ram 2 Tera 4gb Video 17 Pulgada Touchscreen", "$ 1.420.000", 1420000, "12 cuotas de $ 283.325", R.drawable.portatil4, new Categoria(4,"Portatil")),
                new Producto(10,"Apple Macbook Pro Mf839ll/a Portátil De 13,3 Pulgadas","$ 5.910.777",5910777,"12 cuotas de $ 492.565",R.drawable.portatil5,new Categoria(4,"Portatil")),
                new Producto(11,"Psn Card Americana 10 Usd Codigo Digital Ps3 Ps4 Psvita","$ 32.900",32900,"36 cuotas de $ 914",R.drawable.consolas1,new Categoria(2,"Consolas y video juegos")),
                new Producto(12,"Control Ps3 Inalámbrico Play Station 3 Dualshock Sixaxis","$ 49.900",49900,"36 cuotas de $ 1.386",R.drawable.consolas2,new Categoria(2,"Consolas y video juegos")),
                new Producto(13,"Grand Theft Auto 5 Gta V Xbox One. Entrega Inmediata","$ 134.900",134900,"36 cuotas de $ 3.747",R.drawable.consolas3,new Categoria(2,"Consolas y video juegos")),
                new Producto(14,"Xbox 360+ 500gb+ 2 Controles+carga Y Juega +90 Juegos","$ 665.000",665000,"36 cuotas de $ 18.472",R.drawable.consolas5,new Categoria(2,"Consolas y video juegos")),
                new Producto(15,"Reloj Q&q Analogo Dg14j004y Hombre","$ 199.900",199900,"36 cuotas de $ 5.553",R.drawable.relojes1,new Categoria(6,"Relojes")),
                new Producto(16,"Reloj Casio Edifice Redbull - Envío Gratis - Obsequio","$ 229.900",229900,"36 cuotas de $ 6.386",R.drawable.relojes2,new Categoria(6,"Relojes")),
                new Producto(17,"Reloj Fossil Ch2565 Acero Inoxidable Cuero Cafe Cronografo","$ 299.990",229900,"36 cuotas de $ 8.333",R.drawable.relojes3,new Categoria(6,"Relojes")),
                new Producto(18,"Reloj Fossil Fs4656 Resina Cuero Cafe Cronografo Original","$ 334.990",334990,"36 cuotas de $ 9.305",R.drawable.relojes4,new Categoria(6,"Relojes")),
                new Producto(19,"Reloj Polar M400 + Banda Pulsometro H7. Entrega Inmediata.","$ 585.900",585900,"36 cuotas de $ 16.275",R.drawable.relojes5,new Categoria(6,"Relojes")),
                new Producto(20,"Reloj Fossil Hombre Cuero Jr 1390 Cronografo ","$ 449.900",449900,"36 cuotas de $ 12.497",R.drawable.relojes6,new Categoria(6,"Relojes")),
                new Producto(21,"Televisor Sony Hd Smart De 32 Con Wi-fi - Kdl-32w607d","$ 1.139.000",1139000,"36 cuotas de $ 31.638",R.drawable.televisor1,new Categoria(3,"Televisores")),
                new Producto(22,"Televisor Sony 4k Uhd De 55¨ Android Tv - Xbr-55x707d","$ 5.149.000",5149000,"36 cuotas de $ 143.028",R.drawable.televisor2,new Categoria(3,"Televisores")),
                new Producto(23,"Televisor Led 32t15 Lcd T2 Usb","$ 689.900",689900,"36 cuotas de $ 19.163",R.drawable.televisor3,new Categoria(3,"Televisores")),
                new Producto(24,"Televisor Samsung 55mu6500 55 Pulg Curvo 4k Smart Tv 2017","$ 2.829.999",2829999,"",R.drawable.televisor4,new Categoria(3,"Televisores")),
                new Producto(25,"Televisor Samsung 32 Smart Tv 32j4300 Led-negro ","$ 983.564",983564,"36 cuotas de $ 27.321",R.drawable.televisor5,new Categoria(3,"Televisores")),
                new Producto(26,"Celular Motorola Moto G5 Plus 32gb Camara 12mp 4k Ultra Hd","$ 799.900",799900,"36x $ 22.219",R.drawable.celular1,new Categoria(5,"Celulares")),
                new Producto(27,"Samsung Galaxy S7 Edge 4gb Ram 32gb Int 1 Año Garantia","$ 1.599.990",1599990,"36 cuotas de $ 44.444",R.drawable.celular2,new Categoria(5,"Celulares")),
                new Producto(28,"Huawei Mate 9 Lite 3gb Ram Huella 32gb + Factura + Regalos","$ 819.900",819900,"36 cuotas de $ 22.775",R.drawable.celular3,new Categoria(5,"Celulares")),
                new Producto(29,"Huawei P9 Lite Dual Sim 13mp Android Lector De Huella 4g Lte","$ 599.990",599990,"36x $ 16.666",R.drawable.celular4,new Categoria(5,"Celulares")),
                new Producto(30,"Sony Xperia Z5 Premium Dual Sim Pantalla 4k 4g Lte","$ 1.379.900",1379900,"36 cuotas de $ 38.330",R.drawable.celular5,new Categoria(5,"Celulares")),
        };

        return listaProducto;

    }
}
