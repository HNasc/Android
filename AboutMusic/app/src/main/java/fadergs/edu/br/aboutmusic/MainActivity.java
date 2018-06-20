package fadergs.edu.br.aboutmusic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private TextView txtTeste;
    private LinearLayout baseProgressBar;
    //private String[] musica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupera referencia dos campos
        txtTeste = (TextView) findViewById(R.id.txtTeste);
        baseProgressBar = (LinearLayout) findViewById(R.id.baseProgressBar);
    }

    public void PesquisarMusica(View view) {
        String musica = ((EditText) findViewById(R.id.edtPesquisa)).getText().toString();
        if (!musica.isEmpty()) {
            new ConsultaMusicaVagalume().execute(musica, musica);
        }
    }

    private class ConsultaMusicaVagalume extends AsyncTask<String, Void, List<Musica>>{
        @Override
        protected void onPreExecute() {
            baseProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Musica> doInBackground(String... params) {
            /*String url = "https://api.vagalume.com.br/search.php"; https://api.vagalume.com.br/searchvamos%20fugir*/
            //String url = "https://api.vagalume.com.br/search";
            //String musica = ".excerpt?q=" + params[1];
            //String limite = "&limit=1";
            //String urlCompleta = url + musica + limite;
            String urlCompleta = "http://api.postmon.com.br/v1/cep/91750100";
            List<Musica> musicas;

            try{

                HttpURLConnection conexao = conectar(urlCompleta);
                int resposta = conexao.getResponseCode();

                if (resposta == HttpURLConnection.HTTP_OK){
                    InputStream is = conexao.getInputStream();

                    //bytesParaString converte os bytes para String
                    //e do String estou convertendo em JSON
                    JSONObject json = new JSONObject(bytesParaString(is));

                    final Cursor cursor;

                    //A partir do JSON, posso acessar todos os elementos do objeto
                    //Abaixo está acessando a "musica"
                    JSONObject response = json.getJSONObject("response");
                    JSONArray docs = response.getJSONArray("docs");
                    JSONObject doc = docs.getJSONObject(0);

                    //return result = doc.getString("title");
                    musicas = getMusicas(json);
                    return musicas;
                }

            } catch (Exception e){
                Log.e("TAG_ASYNC_TASK", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Musica> result) {
            super.onPostExecute(result);
            baseProgressBar.setVisibility(View.INVISIBLE);
            //txtTeste.setText(s);
            if(result.size() > 0){
                lista = (ListView) findViewById(R.id.listView);
                lista.setAdapter(new ArrayAdapter<Musica>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, result));
                //ArrayAdapter<Musica> adapter = new ArrayAdapter<Musica>(
                 // MainActivity.this,
                  //R.layout.item_lista);

                //SimpleCursorAdapter adaptador = new SimpleCursorAdapter(MainActivity.this, R.layout.item_lista, ,)

                //lista.setAdapter(adapter);
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainActivity.this).setTitle("Atenção")
                        .setMessage("Não foi possivel acessar essas informções...")
                        .setPositiveButton("OK", null);
                builder.create().show();
            }



        }

        private List<Musica> getMusicas(JSONObject json){

            List<Musica> musicas = new ArrayList<Musica>();

            try{
                JSONObject response = json.getJSONObject("response");
                JSONArray docs = response.getJSONArray("docs");

                for (int i = 0; i < docs.length(); i++){
                    JSONObject musica = new JSONObject(docs.getString(i));

                    Log.i("MUSICA_VAGALUME", "nome=" + musica.getString("title"));

                    Musica objMusica = new Musica();
                    objMusica.setNome(musica.getString("title"));
                    objMusica.setArtista(musica.getString("band"));

                     musicas.add(objMusica);

                    return  musicas;
                }

            }catch (JSONException e){
                Log.e("JSON_PARSE", e.getMessage());
            }

            return  musicas;
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
