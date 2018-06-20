package br.edu.fadergs.tarefasassincronas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


//Utilizando AsyncTask
public class MainActivity extends AppCompatActivity {

    private TextView tvEndereco;
    private LinearLayout baseProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperando referência aos campos
        tvEndereco = (TextView) findViewById(R.id.tvEndereco);
        baseProgressBar = (LinearLayout) findViewById(R.id.baseProgressBar);
    }


    public void pesquisarCep(View view) {
        String cep = ((EditText) findViewById(R.id.etCep)).getText().toString();
        if (!cep.isEmpty()) {
            new ConsultaCEPCorreios().execute(cep, cep);
        }
    }


    private class ConsultaCEPCorreios extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String url = "http://cep.republicavirtual.com.br/web_cep.php?formato=json&cep=" + params[1];

            try {

                HttpURLConnection conexao = conectar(url);
                int resposta = conexao.getResponseCode();

                if (resposta == HttpURLConnection.HTTP_OK) {
                    InputStream is = conexao.getInputStream();

                    //bytesParaString converte os bytes para String
                    //e do String estou convertendo em JSON
                    JSONObject json = new JSONObject(bytesParaString(is));

                    //A partir do JSON, posso acessar todos os elementos do objeto
                    //Abaixo está acessando o "logradouro":"Rua America"
                    return json.getString("logradouro");
                }


            } catch (Exception e) {
                Log.e("TAG_ASYNC_TASK", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            baseProgressBar.setVisibility(View.INVISIBLE);
            tvEndereco.setText(s);
        }

        private String bytesParaString(InputStream is) throws IOException {
            byte[] buffer = new byte[1024];

            // O bufferzao vai armazenar todos os bytes lidos
            ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();

            // precisamos saber quantos bytes foram lidos
            int bytesLidos;

            // Vamos lendo de 1KB por vez...
            while ((bytesLidos = is.read(buffer)) != -1) {
                // copiando a quantidade de bytes lidos do buffer para o bufferzão
                bufferzao.write(buffer, 0, bytesLidos);
            }
            return new String(bufferzao.toByteArray(), "UTF-8");
        }


        private HttpURLConnection conectar(String urlArquivo) throws IOException {
            final int SEGUNDOS = 1000;
            URL url = new URL(urlArquivo);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;
        }


    }
}