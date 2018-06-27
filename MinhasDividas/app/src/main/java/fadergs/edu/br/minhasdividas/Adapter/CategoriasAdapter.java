package fadergs.edu.br.minhasdividas.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fadergs.edu.br.minhasdividas.Entidades.Categorias;
import fadergs.edu.br.minhasdividas.R;

public class CategoriasAdapter extends ArrayAdapter<Categorias> {

    private ArrayList<Categorias> categoria;
    private Context context;

    public CategoriasAdapter(Context c, ArrayList<Categorias> objects) {
        super(c, 0, objects);
        this.context = c;
        this.categoria = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (categoria != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.item_categoria, parent, false);

            TextView txtViewCategoria = (TextView) view.findViewById(R.id.txtViewCategoria);
            Categorias categorias = categoria.get(position);
            txtViewCategoria.setText(categorias.getDescricao());

        }

        return view;
    }
}
