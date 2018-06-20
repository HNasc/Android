package br.edu.fadergs.traduzindo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CriaBanco extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "dicionario.db";
    public static final String TABELA = "termos";
    public static final int VERSAO = 1;
    public static final String ID = "_id";
    public static final String INGLES = "termo_ingles";
    public static final String PORTUGUES = "termo_portugues";
    public static final String CLASSE_GRAMATICAL = "classe_gramatical";
    public CriaBanco(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABELA +" ("
                + ID + " integer primary key autoincrement,"
                + INGLES + " text,"
                + PORTUGUES + " text,"
                + CLASSE_GRAMATICAL + " text"
                +")";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
} 