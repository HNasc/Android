package fadergs.edu.br.minhasdividas.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fadergs.edu.br.minhasdividas.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.itCategoria:
                Toast.makeText(MainActivity.this, "Categoria", Toast.LENGTH_SHORT).show();
                abrirCategoria();
                break;
            case R.id.itGrupoCusto:
                Toast.makeText(MainActivity.this, "Grupo Custo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itDividas:
                Toast.makeText(MainActivity.this, "Dividas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itSair:
                Toast.makeText(MainActivity.this, "Sair", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void abrirCategoria(){
        Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);
        startActivity(intent);
    }

}
