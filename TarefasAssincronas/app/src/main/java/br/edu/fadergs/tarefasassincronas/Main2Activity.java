package br.edu.fadergs.tarefasassincronas;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//Utilizando Handler
public class Main2Activity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void baixarImagemWeb(View view){

        new Thread(){
            public void run(){
                try {

                    URL url = new URL("https://www.thiengo.com.br/img/system/logo/thiengo-80-80.png");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    int resposta = connection.getResponseCode();

                    if (resposta ==  HttpURLConnection.HTTP_OK) {

                        //Obtém o stream retornado
                        InputStream input = connection.getInputStream();

                        //Converte em um objeto Bitmap (algo que nosso ImageView entende)
                        final Bitmap imagem  = BitmapFactory.decodeStream(input);

                        Log.i("thread1", "baixou imagem.");

                        final ImageView iv = (ImageView) findViewById(R.id.imagem);

                        handler.post(new Runnable(){
                            public void run(){

                                //Configurando o ImageView a partir de um Bitmap
                                iv.setImageBitmap(imagem);

                            }
                        });


                    } else {
                        Log.i("thread1", "Não baixou imagem. Erro http: " + resposta);
                    }


                }
                catch(MalformedURLException e) {
                    e.printStackTrace();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
