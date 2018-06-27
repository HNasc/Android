package fadergs.edu.br.minhasdividas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fadergs.edu.br.minhasdividas.Entidades.GruposCusto;
import fadergs.edu.br.minhasdividas.R;

public class GruposCustoAdapter extends ArrayAdapter<GruposCusto> {
    private ArrayList<GruposCusto> grupoCusto;
    private Context context;

    public GruposCustoAdapter(Context c, ArrayList<GruposCusto> objects) {
        super(c, 0, objects);
        this.context = c;
        this.grupoCusto = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(grupoCusto != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.item_grupocusto, parent, false);

            TextView txtViewGrupoCusto = (TextView) view.findViewById(R.id.txtViewGrupoCusto);
            GruposCusto gruposCusto = grupoCusto.get(position);
            txtViewGrupoCusto.setText(gruposCusto.getDescricao());
        }

        return view;
    }
}
