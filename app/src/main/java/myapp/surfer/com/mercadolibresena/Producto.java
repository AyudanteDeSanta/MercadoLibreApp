package myapp.surfer.com.mercadolibresena;

/**
 * Created by Surfer on 23/05/2017.
 */

public class Producto {
    int id;
    String nombre;
    String precioConvertido;
    int precio;
    String cuotas;
    int icon;
    Categoria categoria;
    //int iconoSeleccionar;

    public Producto()
    {
        super();
    }
    public Producto(int id, String nombre, String precioConvertido, int precio, String cuotas, int icon
            , Categoria categoria)//, int iconoSeleccionar)
    {
        this.id = id;
        this.nombre = nombre;
        this.precioConvertido = precioConvertido;
        this.precio = precio;
        this.cuotas = cuotas;
        this.icon =icon;
        this.categoria = categoria;
    }

    public int getId(){return id;}
    public String getNombre()
    {
        return nombre;
    }
    public String getPrecioConvertido(){
        return cuotas;
    }
    public int getPrecio(){
        return precio;
    }
    public String getCuotas(){
        return cuotas;
    }
    public int getIcon(){
        return icon;
    }
    public Categoria getCategoria(){return categoria;}
//    public int getIconoSeleccionar(){
//        return iconoSeleccionar;
//    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setPrecioConvertido(String precioConvertido){
        this.precioConvertido = precioConvertido;
    }
    public  void setPrecio(int precio){
        this.precio = precio;
    }
    public void setCuotas(String cuotas){
        this.cuotas=cuotas;
    }
    public void setIcon(int icon) {this.icon=icon;}
    public void setCategoria(Categoria categoria){
        this.categoria=categoria;
    }
    //public void setIconoSeleccionar(int iconoSeleccionar) {this.iconoSeleccionar=iconoSeleccionar;}
}
