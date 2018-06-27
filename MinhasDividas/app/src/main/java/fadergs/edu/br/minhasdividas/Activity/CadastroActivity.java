package fadergs.edu.br.minhasdividas.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import fadergs.edu.br.minhasdividas.DAO.ConfiguracaoFirebase;
import fadergs.edu.br.minhasdividas.Entidades.Categorias;
import fadergs.edu.br.minhasdividas.Entidades.Usuarios;
import fadergs.edu.br.minhasdividas.Helper.Base64Custom;
import fadergs.edu.br.minhasdividas.Helper.Preferencias;
import fadergs.edu.br.minhasdividas.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadNome;
    private EditText edtCadSobrenome;
    private EditText edtCadNascimento;
    private EditText edtCadEmail;
    private EditText edtCadSenha;
    private EditText edtCadConfSenha;
    private Button btnSalvar;

    private Usuarios usuarios;
    private Categorias categorias;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtCadNome = (EditText) findViewById(R.id.edtCadNome);
        edtCadSobrenome = (EditText) findViewById(R.id.edtCadSobrenome);
        edtCadNascimento = (EditText) findViewById(R.id.edtCadNascimento);
        edtCadEmail = (EditText) findViewById(R.id.edtCadEmail);
        edtCadSenha = (EditText) findViewById(R.id.edtCadSenha);
        edtCadConfSenha = (EditText) findViewById(R.id.edtCadConfSenha);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);



        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCadSenha.getText().toString().equals(edtCadConfSenha.getText().toString())){
                    usuarios = new Usuarios();
                    usuarios.setNome(edtCadNome.getText().toString());
                    usuarios.setSobrenome(edtCadSobrenome.getText().toString());
                    usuarios.setNascimento(edtCadNascimento.getText().toString());
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setSenha(edtCadSenha.getText().toString());

                    cadastrarUsuario();

                }else {
                    Toast.makeText(CadastroActivity.this, "Senhas não se correspondem", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    String idUsuario = Base64Custom.codificarBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId((idUsuario));
                    usuarios.salvar();
                    criarCategoriasPadroes(idUsuario);

                    Preferencias preferencias = new Preferencias(CadastroActivity.this);
                    preferencias.salvarUsuarioPreferencias(idUsuario, usuarios.getNome());

                    abrirLoginUsuario();

                }else {
                    String exception = "";
                    try{

                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        exception = "Digite uma senha mais forte, contento no mínimo 8 caracteres de letras e números";

                    }catch (FirebaseAuthInvalidCredentialsException e){
                        exception = "O e-mai digitado é inválido, digite um novo e-mail";

                    }catch (FirebaseAuthUserCollisionException e){
                        exception = "O e-mail já está cadastrado";

                    }catch (Exception e){
                        exception = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();

                    }

                    Toast.makeText(CadastroActivity.this,"Erro: " + exception, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void criarCategoriasPadroes(String idUsuario){
        categorias = new Categorias();
        categorias.setPadrao("S");

        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        categorias.setDescricao("Lazer");
        referenciaFirebase.child("usuario").child(String.valueOf(idUsuario)).child("categoria").child(categorias.getDescricao().toString()).setValue(categorias);
        categorias.setDescricao("Vestimenta");
        referenciaFirebase.child("usuario").child(String.valueOf(idUsuario)).child("categoria").child(categorias.getDescricao().toString()).setValue(categorias);
        categorias.setDescricao("Alimentação");
        referenciaFirebase.child("usuario").child(String.valueOf(idUsuario)).child("categoria").child(categorias.getDescricao().toString()).setValue(categorias);
        categorias.setDescricao("Locomoção");
        referenciaFirebase.child("usuario").child(String.valueOf(idUsuario)).child("categoria").child(categorias.getDescricao().toString()).setValue(categorias);
        categorias.setDescricao("Estudo");
        referenciaFirebase.child("usuario").child(String.valueOf(idUsuario)).child("categoria").child(categorias.getDescricao().toString()).setValue(categorias);
        categorias.setDescricao("Outros");
        referenciaFirebase.child("usuario").child(String.valueOf(idUsuario)).child("categoria").child(categorias.getDescricao().toString()).setValue(categorias);
    }

}
