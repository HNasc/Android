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

import com.google.firebase.auth.FirebaseAuth;

import fadergs.edu.br.minhasdividas.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth usuarioFirebase;

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
                abrirCategoria();
                break;
            case R.id.itGrupoCusto:
                Toast.makeText(MainActivity.this, "Grupo Custo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itDividas:
                Toast.makeText(MainActivity.this, "Dividas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itSair:
                deslogarUsuario();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void abrirCategoria(){
        Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);
        startActivity(intent);
    }

    private void deslogarUsuario(){
        usuarioFirebase.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
