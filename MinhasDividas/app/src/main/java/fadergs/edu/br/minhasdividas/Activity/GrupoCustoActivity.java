package fadergs.edu.br.minhasdividas.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fadergs.edu.br.minhasdividas.Adapter.GruposCustoAdapter;
import fadergs.edu.br.minhasdividas.DAO.ConfiguracaoFirebase;
import fadergs.edu.br.minhasdividas.Entidades.GruposCusto;
import fadergs.edu.br.minhasdividas.Helper.Preferencias;
import fadergs.edu.br.minhasdividas.R;

public class GrupoCustoActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<GruposCusto> adapter;
    private ArrayList<GruposCusto> gruposCustos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerGrupoCusto;
    private Button btnGCVoltar;
    private Button btnGCAdd;
    private AlertDialog alerta;
    private GruposCusto gruposCustoExcluir;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_custo);

        gruposCustos = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listViewGruposCusto);
        adapter = new GruposCustoAdapter(this, gruposCustos);
        listView.setAdapter(adapter);
        preferencias = new Preferencias(GrupoCustoActivity.this);
        firebase = ConfiguracaoFirebase.getFirebase().child("usuario").child(String.valueOf(preferencias.getId())).child("grupocusto");

        valueEventListenerGrupoCusto = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gruposCustos.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    GruposCusto newGruposCusto = dados.getValue(GruposCusto.class);

                    gruposCustos.add(newGruposCusto);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        btnGCVoltar = (Button) findViewById(R.id.btnGCVoltar);
        btnGCVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarTelaInicial();
            }
        });

        btnGCAdd = (Button) findViewById(R.id.btnGCAdd);
        btnGCAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastro();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                firebase = ConfiguracaoFirebase.getFirebase().child("usuario").child(String.valueOf(preferencias.getId()));
                gruposCustoExcluir = adapter.getItem(i);

                //Alerta: Questiona se o usuário quer remover o item
                AlertDialog.Builder builder = new AlertDialog.Builder(GrupoCustoActivity.this);
                builder.setTitle("Confirma Exclusão");
                builder.setMessage("Você deseja excluir o grupo de custo " + gruposCustoExcluir.getDescricao().toString() + "?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebase.child("grupocusto").child(gruposCustoExcluir.getDescricao().toString()).removeValue();
                        Toast.makeText(GrupoCustoActivity.this, "Grupo de custo removido com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(GrupoCustoActivity.this, "Grupo de custo não removida!", Toast.LENGTH_SHORT).show();
                    }
                });

                alerta = builder.create();
                alerta.show();
                return true;
            }
        });
    }

    private void voltarTelaInicial(){
        Intent intent = new Intent(GrupoCustoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void abrirTelaCadastro(){
        Intent intent = new Intent(GrupoCustoActivity.this, CadGrupoCustoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerGrupoCusto);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerGrupoCusto);
    }
}
