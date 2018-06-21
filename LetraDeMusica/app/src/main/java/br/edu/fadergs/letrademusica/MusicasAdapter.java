package br.edu.fadergs.letrademusica;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MusicasAdapter extends ArrayAdapter{
    List list = new ArrayList();
    public MusicasAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(Musica object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        MusicaHolder musicaHolder;
        if (row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.item_lista, parent, false);
            musicaHolder = new MusicaHolder();
            musicaHolder.txtId = (TextView) row.findViewById(R.id.txtId);
            musicaHolder.txtNome = (TextView) row.findViewById(R.id.txtNome);
            musicaHolder.txtArtista = (TextView) row.findViewById(R.id.txtArtista);
            row.setTag(musicaHolder);
        }else{
            musicaHolder = (MusicaHolder) row.getTag();
        }
        Musica musica = (Musica) this.getItem(position);
        musicaHolder.txtId.setText(musica.getId());
        musicaHolder.txtNome.setText(musica.getNome());
        musicaHolder.txtArtista.setText(musica.getArtista());

        return row;
    }

    static class MusicaHolder{
        TextView txtId, txtNome, txtArtista;
    }
}
