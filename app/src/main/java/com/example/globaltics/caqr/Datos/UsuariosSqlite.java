package com.example.globaltics.caqr.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Sam on 19/04/2017.
 */

public class UsuariosSqlite extends SQLiteOpenHelper {
    private SQLiteDatabase databasew = getWritableDatabase();
    private SQLiteDatabase databaser = getReadableDatabase();
    public UsuariosSqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(){
        databasew.execSQL("CREATE TABLE IF NOT EXISTS USERS(Id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR , codigo VARCHAR unique, image VARCHAR, tipo VARCHAR)");
    }
    public void insertData(String nombre, String codigo, String imagen, String tipo){
        String sql = "INSERT INTO USERS VALUES (NULL,?,?,?,?)";
        SQLiteStatement statement = databasew.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,nombre);
        statement.bindString(2,codigo);
        statement.bindString(3,imagen);
        statement.bindString(4,tipo);
        statement.executeInsert();
    }
    public void deleteData(String codigo){
        String sql ="DELETE FROM USERS WHERE codigo=?";
        SQLiteStatement statement = databasew.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,codigo);
        statement.executeUpdateDelete();
    }
    public Cursor getData(String sql){
        return databaser.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
