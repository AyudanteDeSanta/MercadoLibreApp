package myapp.surfer.com.mercadolibresena;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity {
    ListView lvProductos;
    public static List<Producto> productosFactura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Categoria listaCategorias[] = new Categoria[]{
                new Categoria(1,"Computadores"),
                new Categoria(2,"Consolas y video juegos"),
                new Categoria(3,"Televisores"),
                new Categoria(4,"Portatil"),
                new Categoria(5,"Celulares"),
                new Categoria(6,"Relojes"),
        };

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int posicionCategoria=0;
        if (bundle!= null)
        {
            String nombreCategoria = bundle.getString("NOMBRE_CATEGORIA");
            setTitle(nombreCategoria);
            //Log.d("bundle", nombreCategoria);
            for (Categoria fila :listaCategorias) {
                //Log.d("Loop", fila.nombre);
                if (Objects.equals(fila.nombre, nombreCategoria)) {
                    posicionCategoria = fila.id;
                    break;
                }
            }
        }

        if (posicionCategoria!=0)
        {
            posicionCategoria=posicionCategoria-1;
        }

        if (productosFactura==null)
        {
            productosFactura = new ArrayList<Producto>();
        }
        if (productosFactura.size()==0)
        {
            productosFactura = new ArrayList<Producto>();
        }

        SesionSharedPreferences sesion = new SesionSharedPreferences(getApplicationContext());
        int usuarioLogueado = sesion.obtenerUsuarioLogueado();

        Producto listaProducto[] = CrearProductos(listaCategorias[posicionCategoria]);
        ProductoAdapter adapter = new ProductoAdapter(this, R.layout.producto_linea, listaProducto,productosFactura, true, usuarioLogueado);

        lvProductos = (ListView)findViewById(R.id.lvProductos);
        lvProductos.setAdapter(adapter);

    }

    public static Producto[] CrearProductos(Categoria categoria)
    {
        Producto listaProducto[] = new Producto[0];
        Log.d("CrearProductos", categoria.nombre);
        switch (categoria.nombre)
        {
            case "Computadores":
                listaProducto = new Producto[]{
                        new Producto(1,"Pc Gamer Amd A8 7600 8gb 2tb Radeon R7 Combo Monitor 21.5","$ 1.479.900",1479900,"12 cuotas de $ 123.325",R.drawable.computador1,categoria),
                        new Producto(2,"Equipo Escritorio Celeron 3900 2.8ghz 4gb 1tb Monitor Lg 22 ","$ 929.900",929900,"36 cuotas de $ 25.831",R.drawable.computador2,categoria),
                        new Producto(3,"Computador Core I5 De 7a 3,0 Ghz Con Monitor 22 / 8gb","$ 1.999.900",1999900,"36 cuotas de $ 55.553",R.drawable.computador3,categoria),
                        new Producto(4,"Computador De Escritorio Intel Séptima Generación I3 7100","$ 1.420.000",1420000,"36 cuotas de $ 39.444",R.drawable.computador4,categoria),
                        new Producto(5,"Pc Gamer Titan V2: Intel Core I5, Gtx 1060 3gb, 8gb Ram, 1tb","$ 2.699.000",2699000,"36 cuotas de $ 74.972",R.drawable.computador5,categoria)
                };
                Log.d("Create", "Computadores");
                break;
            case "Portatil":
                listaProducto = new Producto[]{
                        new Producto(6,"Portátil Hp Amd A6 Quad Core 4gb 500gb Dvd Gamer Windows 10", "$ 1.299.900", 1299900, "36 cuotas de $ 22.219", R.drawable.portatil1, categoria),
                        new Producto(7,"Portatil Lenovo 110 Celeron 4gb Ram 500gb 14 Win10 + Morral", "$ 929.900", 929900, "36 cuotas de $ 22.219", R.drawable.portatil2, categoria),
                        new Producto(8,"Dell Core I7 16g Ram 2 Tera 4gb Video 17 Pulgada Touchscreen", "$ 1.999.900", 1999900, "12 cuotas de $ 274.992", R.drawable.portatil3, categoria),
                        new Producto(9,"Dell Core I7 16g Ram 2 Tera 4gb Video 17 Pulgada Touchscreen", "$ 1.420.000", 1420000, "12 cuotas de $ 283.325", R.drawable.portatil4, categoria),
                        new Producto(10,"Apple Macbook Pro Mf839ll/a Portátil De 13,3 Pulgadas","$ 5.910.777",5910777,"12 cuotas de $ 492.565",R.drawable.portatil5,categoria)
                };
                Log.d("Create", "Portatil");
                break;

            case "Consolas y video juegos":
                listaProducto = new Producto[]{
                        new Producto(11,"Psn Card Americana 10 Usd Codigo Digital Ps3 Ps4 Psvita","$ 32.900",32900,"36 cuotas de $ 914",R.drawable.consolas1,categoria),
                        new Producto(12,"Control Ps3 Inalámbrico Play Station 3 Dualshock Sixaxis","$ 49.900",49900,"36 cuotas de $ 1.386",R.drawable.consolas2,categoria),
                        new Producto(13,"Grand Theft Auto 5 Gta V Xbox One. Entrega Inmediata","$ 134.900",134900,"36 cuotas de $ 3.747",R.drawable.consolas3,categoria),
                        new Producto(14,"Xbox 360+ 500gb+ 2 Controles+carga Y Juega +90 Juegos","$ 665.000",665000,"36 cuotas de $ 18.472",R.drawable.consolas5,categoria)
                };
                break;
            case "Relojes":
                listaProducto = new Producto[]{
                        new Producto(15,"Reloj Q&q Analogo Dg14j004y Hombre","$ 199.900",199900,"36 cuotas de $ 5.553",R.drawable.relojes1,categoria),
                        new Producto(16,"Reloj Casio Edifice Redbull - Envío Gratis - Obsequio","$ 229.900",229900,"36 cuotas de $ 6.386",R.drawable.relojes2,categoria),
                        new Producto(17,"Reloj Fossil Ch2565 Acero Inoxidable Cuero Cafe Cronografo","$ 299.990",229900,"36 cuotas de $ 8.333",R.drawable.relojes3,categoria),
                        new Producto(18,"Reloj Fossil Fs4656 Resina Cuero Cafe Cronografo Original","$ 334.990",334990,"36 cuotas de $ 9.305",R.drawable.relojes4,categoria),
                        new Producto(19,"Reloj Polar M400 + Banda Pulsometro H7. Entrega Inmediata.","$ 585.900",585900,"36 cuotas de $ 16.275",R.drawable.relojes5,categoria),
                        new Producto(20,"Reloj Fossil Hombre Cuero Jr 1390 Cronografo ","$ 449.900",449900,"36 cuotas de $ 12.497",R.drawable.relojes6,categoria)

                };
                break;
            case "Televisores":
                listaProducto = new Producto[]{
                    new Producto(21,"Televisor Sony Hd Smart De 32 Con Wi-fi - Kdl-32w607d","$ 1.139.000",1139000,"36 cuotas de $ 31.638",R.drawable.televisor1,categoria),
                        new Producto(22,"Televisor Sony 4k Uhd De 55¨ Android Tv - Xbr-55x707d","$ 5.149.000",5149000,"36 cuotas de $ 143.028",R.drawable.televisor2,categoria),
                        new Producto(23,"Televisor Led 32t15 Lcd T2 Usb","$ 689.900",689900,"36 cuotas de $ 19.163",R.drawable.televisor3,categoria),
                        new Producto(24,"Televisor Samsung 55mu6500 55 Pulg Curvo 4k Smart Tv 2017","$ 2.829.999",2829999,"",R.drawable.televisor4,categoria),
                        new Producto(25,"Televisor Samsung 32 Smart Tv 32j4300 Led-negro ","$ 983.564",983564,"36 cuotas de $ 27.321",R.drawable.televisor5,categoria),

                };
                break;
            case "Celulares":
                listaProducto = new Producto[]{
                        new Producto(26,"Celular Motorola Moto G5 Plus 32gb Camara 12mp 4k Ultra Hd","$ 799.900",799900,"36x $ 22.219",R.drawable.celular1,categoria),
                        new Producto(27,"Samsung Galaxy S7 Edge 4gb Ram 32gb Int 1 Año Garantia","$ 1.599.990",1599990,"36 cuotas de $ 44.444",R.drawable.celular2,categoria),
                        new Producto(28,"Huawei Mate 9 Lite 3gb Ram Huella 32gb + Factura + Regalos","$ 819.900",819900,"36 cuotas de $ 22.775",R.drawable.celular3,categoria),
                        new Producto(29,"Huawei P9 Lite Dual Sim 13mp Android Lector De Huella 4g Lte","$ 599.990",599990,"36x $ 16.666",R.drawable.celular4,categoria),
                        new Producto(30,"Sony Xperia Z5 Premium Dual Sim Pantalla 4k 4g Lte","$ 1.379.900",1379900,"36 cuotas de $ 38.330",R.drawable.celular5,categoria),


                };
                break;
        }
        
        return listaProducto;

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        //getMenuInflater().inflate(R.menu.);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//
////        if (id==R.id.action_string)
////        {
////
////        }
//
//        return true;
//    }

//

}
