package fadergs.edu.br.minhasdividas.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import fadergs.edu.br.minhasdividas.DAO.ConfiguracaoFirebase;
import fadergs.edu.br.minhasdividas.Entidades.Categorias;
import fadergs.edu.br.minhasdividas.Helper.Preferencias;
import fadergs.edu.br.minhasdividas.R;

public class CadCategoriaActivity extends AppCompatActivity {

    private EditText edtDescCategoria;
    private Button btnSalvar;
    private Button btnVoltar;
    private Categorias categorias;
    private DatabaseReference firebase;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_categoria);

        edtDescCategoria = (EditText) findViewById(R.id.edtDescCategoria);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorias = new Categorias();
                categorias.setDescricao(edtDescCategoria.getText().toString());
                categorias.setPadrao("N");
                //categorias.setValor(Double.ValueOf(edtDescCategoria.getText().toString()));

                salvarCategoria(categorias);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarParaListagem();
            }
        });

    }

    private boolean salvarCategoria(Categorias categoria){

        try{

            preferencias = new Preferencias(CadCategoriaActivity.this);

            firebase = ConfiguracaoFirebase.getFirebase().child("usuario").child(String.valueOf(preferencias.getId()));
            firebase.child("categoria").child(categoria.getDescricao().toString()).setValue(categorias);

            Toast.makeText(CadCategoriaActivity.this, "Categoria inserida com sucesso!", Toast.LENGTH_LONG).show();
            voltarParaListagem();

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;

        }
    }

    private void voltarParaListagem(){
        Intent intent = new Intent(CadCategoriaActivity.this, CategoriaActivity.class);
        startActivity(intent);
        finish();
    }
}
