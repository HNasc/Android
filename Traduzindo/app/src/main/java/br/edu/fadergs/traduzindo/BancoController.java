package br.edu.fadergs.traduzindo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoController {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context){
        banco = new CriaBanco(context);
    }

    public String insereTermos(String ingles, String portugues, String classegramatical){

        ContentValues valores = new ContentValues();
        db = banco.getWritableDatabase();

        valores.put(CriaBanco.INGLES, ingles);
        valores.put(CriaBanco.PORTUGUES, portugues);
        valores.put(CriaBanco.CLASSE_GRAMATICAL, classegramatical);

        long resultado = db.insert(CriaBanco.TABELA, null, valores);
        db.close();

        if(resultado == -1){
            return "Erro ao inserir registro";
        } else {
            return "Registro Inserido com sucesso";
        }
    }

    public Cursor carregaTermos(){
        Cursor cursor;
        String[] campos = {banco.ID, banco.INGLES, banco.PORTUGUES, banco.CLASSE_GRAMATICAL};

        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public Cursor carregaTermosById(int id){
        Cursor cursor;
        String[] campos = {banco.ID, banco.INGLES, banco.PORTUGUES, banco.CLASSE_GRAMATICAL};

        String where = CriaBanco.ID + " = " + id;

        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, where, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public Cursor carregaUltimoTermo(){
        Cursor cursor;

        String[] campos = {banco.ID, banco.INGLES, banco.PORTUGUES, banco.CLASSE_GRAMATICAL};

        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, "1 desc", "1");

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

}
