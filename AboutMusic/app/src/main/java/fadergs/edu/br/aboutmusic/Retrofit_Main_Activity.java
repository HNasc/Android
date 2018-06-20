package fadergs.edu.br.aboutmusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Main_Activity extends AppCompatActivity {

    private EditText edtPesquisa;
    private TextView txtTeste;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit__main);

        edtPesquisa = (EditText) findViewById(R.id.edtPesquisa);
        txtTeste = (TextView) findViewById(R.id.txtTeste);

        String url = "https://api.vagalume.com.br/search.excerpt?q=";
        //String musica = ".excerpt?q=" + params[1];
        //String limite = "&limit=1";
        //String urlCompleta = url + musica + limite;

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void solicitarMusicas(View view){
        String musica = edtPesquisa.getText().toString();

        VagalumeService service = retrofit.create(VagalumeService.class);

        Call<Musica> call = service.getMusica(musica);

        call.enqueue(new Callback<Musica>() {
            @Override
            public void onResponse(Call<Musica> call, Response<Musica> response) {
                if (response.isSuccessful()){
                    Musica musica = response.body();

                    String strMusica = musica.getNome();

                    txtTeste.setText(strMusica);
                }
            }

            @Override
            public void onFailure(Call<Musica> call, Throwable t) {
                Toast.makeText(Retrofit_Main_Activity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
