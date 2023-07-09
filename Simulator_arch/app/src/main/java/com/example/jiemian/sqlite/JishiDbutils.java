package com.example.jiemian.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.jiemian.bean.Jishi;

import java.util.ArrayList;
import java.util.List;

/**
 * 中药材增删改查
 */
public class JishiDbutils {
   public static final String DB_NAME="medic_dbname";
   public static final int VERSION=1;
   private static JishiDbutils sqliteDB;
   private SQLiteDatabase db;

   private JishiDbutils(Context context){
      JishiHelper openHelper=new JishiHelper(context,DB_NAME,null,VERSION);
       db=openHelper.getWritableDatabase();
   }
   public synchronized static JishiDbutils getInstance(Context context){
       if(sqliteDB==null){
        sqliteDB=new JishiDbutils(context);
       }
       return sqliteDB;
   }

   public void delete(Context context,String id){
       JishiHelper openHelper=new JishiHelper(context,DB_NAME,null,VERSION);
       db=openHelper.getReadableDatabase();
       db.delete("Medic","id=?",new String[]{id});
   }

    public void change(Context context, Jishi medic){
        JishiHelper openHelper=new JishiHelper(context,DB_NAME,null,VERSION);
     db=openHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id",medic.getId());
        values.put("timu",medic.getTimu());
        values.put("image",medic.getImage());
        values.put("xiangqing",medic.getXiangqing());
        values.put("timest",medic.getTimest());
        db.update("Medic",values,"id=?",new String[]{medic.getId()+""});
    }

   public int insert(Jishi medic){
       try {
           db.execSQL("insert into Medic(timu,image,xiangqing,timest) values(?,?,?,?)",new String[]{
                   medic.getTimu(),
                   medic.getImage(),
                   medic.getXiangqing(),
                   medic.getTimest()
           });
           return 0;
       }catch (Exception e){
           Log.d("����", e.getMessage().toString());
           return -1;
       }
   }
    /**
     * 查询所有数据
     * @return
     */
    public List<Jishi> load(){
          List<Jishi>list=new ArrayList<Jishi>();
          Cursor cursor=db.query("Medic",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Jishi medic=new Jishi();
               medic.setId(cursor.getInt(cursor.getColumnIndex("id")));
               medic.setTimu(cursor.getString(cursor.getColumnIndex("timu")));
               medic.setImage(cursor.getString(cursor.getColumnIndex("image")));
               medic.setXiangqing(cursor.getString(cursor.getColumnIndex("xiangqing")));
                medic.setTimest(cursor.getString(cursor.getColumnIndex("timest")));
               Log.e("app", "medic = " + medic.toString());
               list.add(medic);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public List<Jishi> loadByName(String name){
        List<Jishi>list=new ArrayList<Jishi>();
        Cursor cursor=db.query("Medic",null,null,null,null,null,null);
       if(cursor.moveToFirst()){
           do {
           if(cursor.getString(cursor.getColumnIndex("timu")).indexOf(name)!=-1){
               Jishi medic=new Jishi();
               medic.setId(cursor.getInt(cursor.getColumnIndex("id")));
               medic.setTimu(cursor.getString(cursor.getColumnIndex("timu")));
               medic.setImage(cursor.getString(cursor.getColumnIndex("image")));
               medic.setXiangqing(cursor.getString(cursor.getColumnIndex("xiangqing")));
               medic.setTimest(cursor.getString(cursor.getColumnIndex("timest")));
              list.add(medic);
           }
          }while (cursor.moveToNext());
       }
        cursor.close();
       return list;
    }
}
