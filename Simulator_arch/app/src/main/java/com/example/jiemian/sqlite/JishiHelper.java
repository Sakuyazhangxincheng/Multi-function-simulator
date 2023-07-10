package com.example.jiemian.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 中药材数据库
 */
public class JishiHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER ="create table Medic("
            + "id integer primary key autoincrement, "
            + "timu text, "
            + "image text, "
            + "xiangqing text, "
            + "timest text)";


    public JishiHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
