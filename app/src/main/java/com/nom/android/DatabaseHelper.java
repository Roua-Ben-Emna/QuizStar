package com.nom.android;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.SparseIntArray;

public class  DatabaseHelper extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "QuizBd";
    // below int is our database version
    private static final int DB_VERSION = 1;

    // creating a constructor for our database handler.
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE user(ID INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,password TEXT,email TEXT)");
        db.execSQL("CREATE TABLE score (ID INTEGER PRIMARY KEY AUTOINCREMENT ,IDUser TEXT, maxScore TEXT,FOREIGN KEY(IDUser) REFERENCES user (ID))");
        // at last we are calling a exec sql
        // method to execute above sql query

    }

    // this method is use to add new course to our sqlite database.


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS score");
        onCreate(db);
    }

    public Integer getId(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor ids = sqLiteDatabase.rawQuery("SELECT ID FROM user WHERE email=? ", new String[]{email});
        ids.moveToFirst();
        int id= ids.getInt(0);
        String s=String.valueOf(id);
        Log.i("test : id", s);
        return id;
    }

    public boolean checkEmail(String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE email=?", new String[]{email});
        if(cursor.getCount() > 0){
            return false;
        }else{
            return true;
        }

    }

    public boolean scoreInsert(String email){
        int id=getId(email);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDUser",id);
        contentValues.put("maxScore",0);

        long result = sqLiteDatabase.insert("score", null, contentValues);

        if(result == -1 ){

            return false;
        }else{
            return true;
        }


    }

    public boolean Insert(String name,String pwd,String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", name);
        contentValues.put("password", pwd);
        contentValues.put("email", email);

        long result = sqLiteDatabase.insert("user", null, contentValues);

        if(result == -1 ){
            return false;
        }else if(scoreInsert(email)){
            scoreInsert(email);
            return true;
        }else{return false;}

    }

    public boolean CheckLogin(String email,String pwd){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE email=? AND password=?", new String[]{email,pwd});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }

    }

    public String recoverName (String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT username FROM user WHERE email=? ", new String[]{email});
        cursor.moveToFirst();

        String x = cursor.getString(cursor.getColumnIndexOrThrow("username"));
        Log.i("test : name", x);
        return x;
    }

    public String recoverScore (String email){
       int id=getId(email);
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT maxScore FROM score WHERE IDUser=?",new String [] {String.valueOf(id)});
        cursor.moveToFirst();
        String x = cursor.getString(cursor.getColumnIndexOrThrow("maxScore"));
        Log.i("test : score", x);
        return x;
    }

    public boolean updateScore(String email,String score){
        int id=getId(email);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDUser",id);
        contentValues.put("maxScore",score);
        sqLiteDatabase.update("score",contentValues,"ID = ?",new String [] {String.valueOf(id)});
        return true;
    }
}