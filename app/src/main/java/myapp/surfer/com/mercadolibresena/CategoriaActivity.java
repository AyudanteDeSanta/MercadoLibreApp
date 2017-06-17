package myapp.surfer.com.mercadolibresena;

//import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.view.GravityCompat;

import java.util.ArrayList;

public class CategoriaActivity extends BaseActivity {
    ListView lvCategoria;
    ImageView ivFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        Categoria listaCategorias[] = new Categoria[]{
                new Categoria(1,"Computadores"),
                new Categoria(2,"Consolas y video juegos"),
                new Categoria(3,"Televisores"),
                new Categoria(4,"Portatil"),
                new Categoria(5,"Celulares"),
                new Categoria(6,"Relojes"),
        };

        if (MainActivity.productosFactura==null)
        {
            MainActivity.productosFactura = new ArrayList<Producto>();
        }

        Log.d("categoria", String.valueOf(MainActivity.productosFactura.size()));

        final CategoriaAdapter categoriaAdapter = new CategoriaAdapter(this,R.layout.categoria_linea, listaCategorias);

        lvCategoria = (ListView) findViewById(R.id.lvCategorias);
        lvCategoria.setAdapter(categoriaAdapter);

        lvCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tvCategoria = (TextView)view.findViewById(R.id.tvCategoriaLinea);
                Intent intent = new Intent(CategoriaActivity.this, MainActivity.class);
                intent.putExtra("NOMBRE_CATEGORIA", tvCategoria.getText());
                startActivity(intent);
            }

        });

//        ivFace = (ImageView) findViewById(R.id.ivFacebook);
//        ivFace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, "Comparte esta App!!!");
//                startActivity(Intent.createChooser(intent,"Compartir con"));
//            }
//        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}
