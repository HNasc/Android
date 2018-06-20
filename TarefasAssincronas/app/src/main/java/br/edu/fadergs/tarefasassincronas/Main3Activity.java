package br.edu.fadergs.tarefasassincronas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main3Activity extends AppCompatActivity {

    private EditText edtCep;
    private TextView tvDados;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        edtCep = (EditText) findViewById(R.id.edtCep);
        tvDados = (TextView) findViewById(R.id.tvDados);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.postmon.com.br/v1/cep/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void solicitarEndereco(View view) {

        String cep = edtCep.getText().toString();

        PostmonService service = retrofit.create(PostmonService.class);

        Call<Endereco> call = service.getEndereco(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereço = response.body();

                    String strEndereço = "Cidade: " + endereço.getCidade() + "\n" +
                            "Bairro: " + endereço.getBairro() + "\n" +
                            "Logradouro: " + endereço.getLogradouro();

                    tvDados.setText(strEndereço);
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(Main3Activity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
