package br.edu.fadergs.parsejson;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getJSON(View view){
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://androidtut.comli.com/json_get_data.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }
    }

    public void parseJSON(View view){
        String json_string;
        json_string = "{\"server_response\":[{\"name\":\"Capeta\",\"email\":\"cap@cap.com\",\"mobile\":\"999444\"},{\"name\":\"Jose\",\"email\":\"jose@cap.com\",\"mobile\":\"666999\"}]}";

        if(json_string == null){
            Toast.makeText(getApplicationContext(), "First GET JSON", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, DisplayListView.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
        }

    }
}
