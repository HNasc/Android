package br.edu.fadergs.traduzindo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastraTermos extends AppCompatActivity {

    // EditText
    private EditText termoIngles;
    private EditText termoPortugues;

    // Spinner
    private Spinner classeGramatical;

    // Botoes
    private Button salvar;
    private Button listar;
    private Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_termos);

        // Pega referencias:
        termoIngles = (EditText) findViewById(R.id.edtTermoIngles);
        termoPortugues = (EditText) findViewById(R.id.edtTermoPortugues);
        classeGramatical = (Spinner) findViewById(R.id.spClasseGramatical);
        salvar = (Button) findViewById(R.id.btnSalvar);
        listar = (Button) findViewById(R.id.btnListar);
        cancelar = (Button) findViewById(R.id.btnCancelar);


        // Carrega Spinner:
        String[] classesGramaticais =
                {
                        "Selecione uma Opção",
                        "Substantivo",
                        "Adjetivo",
                        "Verbo",
                        "Advérbio",
                        "Pronome",
                        "Preposição",
                        "Conjunção",
                        "Artigos"
                };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classesGramaticais);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classeGramatical.setAdapter(adapter);

        //Salva dados:
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ValidaCampos()){
                    Toast.makeText(getApplicationContext(), "Impossível salvar. Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
                }else {

                    // Salva registro:
                    BancoController crud = new BancoController(CadastraTermos.this);

                    String termoInglesString = termoIngles.getText().toString();
                    String termoPortuguesString = termoPortugues.getText().toString();
                    String classeGramaticalString = classeGramatical.getSelectedItem().toString();

                    String resultado = crud.insereTermos(termoInglesString, termoPortuguesString, classeGramaticalString);

                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

                    // Exibe registro salvo:
                    Cursor cursor = crud.carregaUltimoTermo();
                    String codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));

                    Intent intent = new Intent(CadastraTermos.this, MostraTermos.class);
                    intent.putExtra("codigo", codigo);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Lista termos:
        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListaTermos();
            }
        });

        //Limpa campos:
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LimpaCampos();
            }
        });

    }

    private boolean ValidaCampos(){

        termoIngles = (EditText) findViewById(R.id.edtTermoIngles);
        termoPortugues = (EditText) findViewById(R.id.edtTermoPortugues);
        classeGramatical = (Spinner) findViewById(R.id.spClasseGramatical);

        String termoInglesInformado = termoIngles.getText().toString();
        String termoPortuguesInformado = termoPortugues.getText().toString();
        String classeGramaticalInformada =  classeGramatical.getSelectedItem().toString();

        if (termoInglesInformado.equals("") || termoPortuguesInformado.equals("") || classeGramaticalInformada.equals("Selecione uma Opção") || classeGramaticalInformada.equals("")){
            return false;
        } else {
            return true;
        }

    }


    private void ListaTermos(){
        Intent intent = new Intent(CadastraTermos.this, ListarTermos.class);
        startActivity(intent);
        finish();
    }

    private void LimpaCampos(){
        termoIngles.setText("");
        termoPortugues.setText("");
        classeGramatical.setSelection(0);
    }
}
