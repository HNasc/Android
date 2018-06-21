package br.edu.fadergs.letrademusica;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class LetraActivity extends AppCompatActivity {

    private TextView txtTitulo, txtMusica;
    private LinearLayout baseProgressBar;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String idMusica;
    MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letra);

        baseProgressBar = (LinearLayout) findViewById(R.id.baseProgressBar);

        idMusica = getIntent().getExtras().getString("idMusica");
        if(!idMusica.isEmpty()){
            new ConsultaLetraVagalume().execute(idMusica,idMusica);
        }

    }

    private class ConsultaLetraVagalume extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            baseProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlCompleta = "https://api.vagalume.com.br/search.php?musid=" + strings[1] + "&apikey={key}";
            try{
                HttpURLConnection conexao = mainActivity.conectar(urlCompleta);
                int resposta = conexao.getResponseCode();

                if(resposta == HttpURLConnection.HTTP_OK){
                    InputStream is = conexao.getInputStream();

                    JSONObject json = new JSONObject(mainActivity.bytesParaString(is));
                    JSONArray mus = json.getJSONArray("mus");

                    return mus.toString();

                }


            }catch (Exception e){
                Log.e("TAG_ASYNC_TASK", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            baseProgressBar.setVisibility(View.INVISIBLE);
            mostrarLetra(s);
        }

        public void mostrarLetra(String letra){

            txtTitulo = (TextView) findViewById(R.id.txtTituloMusica);
            txtMusica = (TextView) findViewById(R.id.txtMusica);

            try{
                jsonArray = new JSONArray(letra);
                jsonObject = jsonArray.getJSONObject(0);

                txtTitulo.setText(jsonObject.getString("name"));
                txtMusica.setText(jsonObject.getString("text"));

            }catch (JSONException ex){
                ex.printStackTrace();
            }

        }
    }
}
