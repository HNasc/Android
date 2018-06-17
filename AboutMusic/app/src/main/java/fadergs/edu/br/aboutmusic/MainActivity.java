package fadergs.edu.br.aboutmusic;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView txtTeste;
    private LinearLayout baseProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupera referencia dos campos
        txtTeste = (TextView) findViewById(R.id.txtTeste);
        baseProgressBar = (LinearLayout) findViewById(R.id.baseProgressBar);
    }

    private class ConsultaMusicaVagalume extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            baseProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String url = "https://api.vagalume.com.br/search.php";
            String artista = "?art=";
            String musica = "&mus=";

            try{

                HttpURLConnection conexao = conectar(url);
                int resposta = conexao.getResponseCode();

                if (resposta == HttpURLConnection.HTTP_OK){
                    InputStream is = conexao.getInputStream();
                }

            } catch (Exception e){
                Log.e("TAG_ASYNC_TASK", e.getMessage());
            }

            return null;
        }

        private HttpURLConnection conectar(String url) throws IOException {
            return  null;
        }

    }
}
