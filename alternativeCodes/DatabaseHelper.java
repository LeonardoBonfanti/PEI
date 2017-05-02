package com.bonfanti.leonardo.pei.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Usuário on 3/24/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    SQLiteDatabase db;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASS = "pass";
    private static final String COLUMN_CLASS = "classroom";
    private static final String COLUMN_TEST = "test";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_DATE = "date";

    private static final String TABLE_CREATE = "create table users (id integer primary key not null , " +
            "name text not null , pass text not null , classroom text not null, test text not null, level text not null, " +
            "date text not null);";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(TABLE_CREATE);
        this.db = sqLiteDatabase;
    }

    public void deleteAll()
    {
        db = this.getWritableDatabase();

        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insertUser(UsersInternDatabase newUser)
    {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        String query = "select * from users";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, newUser.getName());
        values.put(COLUMN_PASS, newUser.getPass());
        values.put(COLUMN_CLASS, newUser.getClassroom());
        values.put(COLUMN_TEST, newUser.getTest());
        values.put(COLUMN_LEVEL, newUser.getLevel());
        values.put(COLUMN_DATE, newUser.getDate());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<String> searchPass(String userName)
    {
        db = this.getReadableDatabase();

        String query = "select * from " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        String name;
        ArrayList<String> pass = new ArrayList<>();
        pass.add("Não encontrado!");

        if(cursor.moveToFirst())
        {
            do
            {
                name = cursor.getString(1);

                if(name.equals(userName))
                    pass.add(cursor.getString(2));

            }while(cursor.moveToNext());
        }

        db.close();

        return pass;
    }
}
