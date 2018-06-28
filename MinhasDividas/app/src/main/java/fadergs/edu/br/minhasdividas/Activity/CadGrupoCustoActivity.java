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
import fadergs.edu.br.minhasdividas.Entidades.GruposCusto;
import fadergs.edu.br.minhasdividas.Helper.Preferencias;
import fadergs.edu.br.minhasdividas.R;

public class CadGrupoCustoActivity extends AppCompatActivity {

    private EditText edtDescGrupoCusto;
    private Button btnSalvar;
    private Button btnVoltar;
    private GruposCusto gruposCusto;
    private DatabaseReference firebase;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_grupo_custo);

        edtDescGrupoCusto = (EditText) findViewById(R.id.edtDescGrupoCusto);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gruposCusto = new GruposCusto();
                gruposCusto.setDescricao(edtDescGrupoCusto.getText().toString());

                salvarGrupoCusto(gruposCusto);

            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarParaListagem();
            }
        });

    }
    private boolean salvarGrupoCusto(GruposCusto grupoCusto){
        try{
            preferencias = new Preferencias(CadGrupoCustoActivity.this);

            firebase = ConfiguracaoFirebase.getFirebase().child("usuario").child(String.valueOf(preferencias.getId()));
            firebase.child("grupocusto").child(grupoCusto.getDescricao().toString()).setValue(gruposCusto);

            Toast.makeText(CadGrupoCustoActivity.this, "Grupo de Custo inserido com sucesso!", Toast.LENGTH_LONG).show();
            voltarParaListagem();

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void voltarParaListagem(){
        Intent intent = new Intent(CadGrupoCustoActivity.this, GrupoCustoActivity.class);
        startActivity(intent);
        finish();
    }
}
