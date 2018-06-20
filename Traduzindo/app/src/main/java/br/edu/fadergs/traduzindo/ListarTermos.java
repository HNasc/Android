package br.edu.fadergs.traduzindo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListarTermos extends AppCompatActivity {

    // Declaração de variaveis globais:
    private ListView lista;
    private Button voltar;
    BancoController crud = new BancoController(ListarTermos.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_termos);

        voltar = (Button)findViewById(R.id.btnVoltar);

        //Lista termos:
        final Cursor cursor = crud.carregaTermos();

        String[] nomeCampos = new String[]{CriaBanco.INGLES, CriaBanco.PORTUGUES};

        // Array com id de cada view da tela
        int[] idViews = new int[]{R.id.termoIngles, R.id.termoPortugues};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(ListarTermos.this, R.layout.item_lista, cursor, nomeCampos, idViews, 0);

        //Configurando adapter
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(adaptador);

        //Voltar para a tela principal (CadastraTermos):
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarParaTelaInicial();
            }
        });
    }

    private void voltarParaTelaInicial(){
        Intent intent = new Intent(ListarTermos.this, CadastraTermos.class);
        startActivity(intent);
        finish();
    }

}
