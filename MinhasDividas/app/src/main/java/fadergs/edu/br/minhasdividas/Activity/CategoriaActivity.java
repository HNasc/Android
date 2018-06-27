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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fadergs.edu.br.minhasdividas.Adapter.CategoriasAdapter;
import fadergs.edu.br.minhasdividas.DAO.ConfiguracaoFirebase;
import fadergs.edu.br.minhasdividas.Entidades.Categorias;
import fadergs.edu.br.minhasdividas.R;

public class CategoriaActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Categorias> adapter;
    private ArrayList<Categorias> categorias;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerCategorias;
    private Button btnCatVoltar;
    private Button btnCatAdd;
    private AlertDialog alerta;
    private Categorias categoriasExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        categorias = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listViewCategorias);
        adapter = new CategoriasAdapter(this, categorias);
        listView.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("categoria");

        valueEventListenerCategorias = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categorias.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Categorias newCategorias = dados.getValue(Categorias.class);

                    categorias.add(newCategorias);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        btnCatVoltar = (Button) findViewById(R.id.btnCatVoltar);
        btnCatVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarTelaInicial();
            }
        });

        btnCatAdd = (Button) findViewById(R.id.btnCatAdd);
        btnCatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastro();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {

                categoriasExcluir = adapter.getItem(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(CategoriaActivity.this);
                builder.setTitle("Confirma Exclusão");
                builder.setMessage("Você deseja excluir a categoria " + categoriasExcluir.getDescricao().toString() + "?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("categoria");
                        firebase.child(categoriasExcluir.getDescricao().toString()).removeValue();

                        Toast.makeText(CategoriaActivity.this, "Categoria removida com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CategoriaActivity.this, "Categoria não removida!", Toast.LENGTH_SHORT).show();
                    }
                });

                alerta = builder.create();
                alerta.show();
                return true;
            }
        });

    }

    private void voltarTelaInicial(){
        Intent intent = new Intent(CategoriaActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirTelaCadastro(){
        Intent intent = new Intent(CategoriaActivity.this, CadCategoriaActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerCategorias);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerCategorias);
    }
}
