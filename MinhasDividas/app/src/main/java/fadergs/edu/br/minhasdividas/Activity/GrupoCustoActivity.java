package fadergs.edu.br.minhasdividas.Activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fadergs.edu.br.minhasdividas.Entidades.GruposCusto;
import fadergs.edu.br.minhasdividas.Helper.Preferencias;
import fadergs.edu.br.minhasdividas.R;

public class GrupoCustoActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<GruposCusto> adapter;
    private ArrayList<GruposCusto> gruposCustos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerCategorias;
    private Button btnCatVoltar;
    private Button btnCatAdd;
    private AlertDialog alerta;
    private GruposCusto gruposCustoExcluir;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_custo);

        gruposCustos = new ArrayList<>();

    }
}
