package myapp.surfer.com.mercadolibresena;

/**
 * Created by Surfer on 23/05/2017.
 */

public class Categoria {
    int id;
    String nombre;

    public Categoria(){super();}

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId(){return id;}
    public String getNombre(){return nombre;}

    public void setId(int id){
        this.id=id;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
}
