package br.edu.fadergs.traduzindo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MostraTermos extends AppCompatActivity {

    // EditText
    private EditText termoIngles;
    private EditText termoPortugues;

    // Spinner
    private EditText classeGramatical;

    // Botão
    private Button voltar;

    // Referenciados
    private Cursor cursor;
    private BancoController crud;
    private String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_termos);

        // Repasse de valores para as variaveis globais:

        codigo = this.getIntent().getStringExtra("codigo");

        crud = new BancoController(this);

        termoIngles = (EditText) findViewById(R.id.edtTermoIngles);
        termoPortugues = (EditText) findViewById(R.id.edtTermoPortugues);
        classeGramatical = (EditText) findViewById(R.id.edtClasseGramatical);

        termoIngles.setEnabled(false);
        termoPortugues.setEnabled(false);
        classeGramatical.setEnabled(false);

        voltar = (Button) findViewById(R.id.btnVoltar);

        cursor = crud.carregaTermosById(Integer.parseInt(codigo));
        termoIngles.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.INGLES)));
        termoPortugues.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.PORTUGUES)));
        classeGramatical.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.CLASSE_GRAMATICAL)));

        // Voltar para tela inicial (CadastraTermos):
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarParaTelaInicial();
            }
        });


    }
    private void voltarParaTelaInicial(){
        Intent intent = new Intent(MostraTermos.this, CadastraTermos.class);
        startActivity(intent);
        finish();
    }

}
