package myapp.surfer.com.mercadolibresena;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Surfer on 24/05/2017.
 */

public class CategoriaAdapter extends ArrayAdapter<Categoria> {

    Context context;
    int resource;
    Categoria[] objects;

    public CategoriaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Categoria[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        CategoriaHolder holder;

        if (row==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource,parent,false);

            holder = new CategoriaHolder();
            holder.tvCategoriaLinea = (TextView) row.findViewById(R.id.tvCategoriaLinea);

            row.setTag(holder);

        }
        else
        {
            holder = (CategoriaHolder)row.getTag();
        }

        Categoria categoria = objects[position];
        holder.tvCategoriaLinea.setText(categoria.nombre);

        return row;
    }

    static class CategoriaHolder
    {
        TextView tvCategoriaLinea;
    }
}
