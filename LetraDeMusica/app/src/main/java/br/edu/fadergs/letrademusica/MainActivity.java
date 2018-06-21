package br.edu.fadergs.letrademusica;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private LinearLayout baseProgressBar;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    MusicasAdapter musicasAdapter;
    ListView listView;
    EditText edtPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupera referencia dos campos
        baseProgressBar = (LinearLayout) findViewById(R.id.baseProgressBar);
    }

    public void PesquisarMusica(View view) {
        String musica = ((EditText) findViewById(R.id.edtPesquisa)).getText().toString();
        if (!musica.isEmpty()) {
            new ConsultaMusicaVagalume().execute(musica, musica);
        }
    }

    private class ConsultaMusicaVagalume extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            baseProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            /*String url = "https://api.vagalume.com.br/search.php"; https://api.vagalume.com.br/searchvamos%20fugir*/
            String url = "https://api.vagalume.com.br/search";
            String musica = ".excerpt?q=" + params[1];
            //String limite = "&limit=1";
            String urlCompleta = url + musica;// + limite;
            //https://api.vagalume.com.br/search.excerpt?q=one
            try{

                HttpURLConnection conexao = conectar(urlCompleta);
                int resposta = conexao.getResponseCode();

                if (resposta == HttpURLConnection.HTTP_OK){
                    InputStream is = conexao.getInputStream();

                    //bytesParaString converte os bytes para String
                    //e do String estou convertendo em JSON
                    JSONObject json = new JSONObject(bytesParaString(is));

                    JSONObject response = json.getJSONObject("response");
                    //JSONArray docs = response.getJSONArray("docs");
                    //JSONObject doc = docs.getJSONObject(0);

                    return response.toString();
                }

            } catch (Exception e){
                Log.e("TAG_ASYNC_TASK", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            baseProgressBar.setVisibility(View.INVISIBLE);

            edtPesquisa = (EditText) findViewById(R.id.edtPesquisa);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(edtPesquisa.getWindowToken(), 0);

            listarMusicas(result);
        }


        public void listarMusicas(String musicas){
            listView = findViewById(R.id.listView);

            musicasAdapter = new MusicasAdapter(MainActivity.this, R.layout.item_lista);
            listView.setAdapter(musicasAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Musica itemSelecionado = (Musica) parent.getItemAtPosition(position);
                    String idMusica = itemSelecionado.getId();
                    chamaLetra(idMusica);
                }
            });

            try {
                jsonObject = new JSONObject(musicas);
                jsonArray = jsonObject.getJSONArray("docs");
                int count = 0;
                String id, nome, artista;

                while (count < jsonArray.length()){
                    JSONObject JO = jsonArray.getJSONObject(count);
                    id = JO.getString("id");
                    nome = JO.getString("title");
                    artista = JO.getString("band");
                    Musica musica = new Musica(id, nome, artista);
                    musicasAdapter.add(musica);

                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void chamaLetra (String idMusica){
            Intent intent = new Intent(MainActivity.this, LetraActivity.class);
            intent.putExtra("idMusica", idMusica);
            startActivity(intent);

        }

    }

    public String bytesParaString(InputStream is) throws IOException {
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

    public HttpURLConnection conectar(String urlArquivo) throws IOException {
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
