package com.nikan.clickpaz.nikantest.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nikan.clickpaz.nikantest.DatabaseModel.DateModel;
import com.nikan.clickpaz.nikantest.DatabaseModel.ShopModel;
import com.nikan.clickpaz.nikantest.DatabaseModel.StatusModel;

/**
 * Created by slim shady on 09/09/2018.
 */



public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String TAG="DatabaseHelper";

    public static final String DatabaseName = "Shop.db";
    public static final String ShopTable = "ShopTask";

    public static final String Col_1 = "ShopID";
    public static final String Col_2 = "ShopTitle";
    public static final String Col_3 = "ShopDetail";
    public static final String Col_4 = "ShopDescription";
    public static final String Col_5 = "ShopMakeBy";
    public static final String Col_6 ="ShopImage";
    public static final String Col_7 ="ShopCost";
    public static final String Col_8 ="ShopNumber";


    public static final String DateTable="DateTable";

    public static final String Date_id ="DateID";
    public static final String Col_day ="ShopDay";
    public static final String Col_time ="ShopTime";

    public static final String StatusTable="StatusTable";

    public static final String Col_status="ShopStatus";
    public static final String Col_order_id="OrderId";
    public static final String Col_id="ItemID";
    public static final String Col_number="OrderNumber";





    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "create table if not exists " + ShopTable + " ( ShopID INTEGER PRIMARY KEY AUTOINCREMENT, ShopTitle TEXT, ShopDetail TEXT, ShopDescription TEXT, ShopMakeBy TEXT, ShopImage TEXT, ShopCost TEXT, ShopNumber TEXT)";

        String createTableTime = "create table if not exists " + DateTable + " ( DateID INTEGER PRIMARY KEY AUTOINCREMENT, ShopDay TEXT, ShopTime TEXT)";


        String createStatusOrder = "create table if not exists " + StatusTable + " ( OrderId INTEGER PRIMARY KEY AUTOINCREMENT, ShopStatus TEXT, ItemID INTEGER, OrderNumber TEXT)";


        db.execSQL(createTableQuery);
        db.execSQL(createTableTime);
        db.execSQL(createStatusOrder);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       /* Log.i(TAG, "Database Version: OLD: "+ oldVersion + " = NEW: "+newVersion);

        if(context.deleteDatabase(DatabaseName))
            Log.i(TAG, "Database Deleted....");
        else
            Log.i(TAG, "Database Not Deleted..");*/

        db.execSQL("DROP TABLE IF EXISTS " + ShopTable);
        db.execSQL("DROP TABLE IF EXISTS " + DateTable);
        db.execSQL("DROP TABLE IF EXISTS " + StatusTable);



        onCreate(db);
    }

    public boolean insertInto(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.insert(ShopTable, null, cv);
        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }



    public boolean insertStatus(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.insert(StatusTable, null, cv);
        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertDateInto(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.insert(DateTable, null, cv);
        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor selectAllDate(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + DateTable ;
        Cursor result = db.rawQuery(query, null);
        return result;
    }


    public Cursor selectAllShop(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + ShopTable ;
        Cursor result = db.rawQuery(query, null);
        return result;
    }




    public Cursor selectAllStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + StatusTable ;
        Cursor result = db.rawQuery(query, null);
        return result;
    }

   /* public void addShop(ShopModel shopModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ShopTitle", shopModel.getTitle());
        values.put("ShopMakeBy",shopModel.getMakeBy());
        values.put("ShopNumber",shopModel.getNumber());
        values.put("ShopCost",shopModel.getCost());
        values.put("ShopDescription",shopModel.getDescription());


        db.insert(TableName,
                null,
                values);

        db.close();
    }*/

    /*public List<ShopModel> getAllData(){

        List<ShopModel> shopModels=new ArrayList<>();

        String query = "SELECT  * FROM " + TableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ShopModel shopModel;
        shopModel=null;

        if (cursor.moveToFirst()) {
            do {
                shopModel = new ShopModel();
                shopModel.setTitle(cursor.getString(0));
                shopModel.setDescription(cursor.getString(1));
                shopModel.setMakeBy(cursor.getString(2));
                shopModel.setCost(cursor.getString(3));
                shopModel.setNumber(cursor.getString(4));




                shopModels.add(shopModel);
            } while (cursor.moveToNext());
        }

        return shopModels;

    }*/

    public Cursor selectSingleData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ShopTitle, ShopCost FROM " + ShopTable;
        Cursor result = db.rawQuery(query, null);


        return result;
    }

    public Cursor selectSpecificId(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT ShopTitle, ShopCost, ShopNumber FROM " +ShopTable+" WHERE "+Col_8+"!='"+"0"+"'" ;
        Cursor result = db.rawQuery(query, null);
        return result;


    }




    public Cursor updateTask(ShopModel td) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + ShopTable
                + " SET "
                + Col_2 + "='" + td.getTitle()
                + "', "
                + Col_3 + "='" + td.getDetail()
                + "', "
                + Col_4 + "='" + td.getDescription()
                + "', "
                + Col_5 + "='" + td.getMakeBy()
                + "', "
                +Col_6 + "='" + td.getImg()
                + "', "
                +Col_7 + "='" + td.getCost()
                + "', "
                +Col_8 + "='" + td.getNumber()


                + "' WHERE " + Col_1 + "='" + td.getId() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }

    public Cursor updateNumber(ShopModel td) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + ShopTable
                + " SET "
                +Col_8 + "='" + td.getNumber()


                + "' WHERE " + Col_1 + "='" + td.getId() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }

    public Cursor updateZero(ShopModel td) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + ShopTable
                + " SET "
                +Col_8 + "='" + td.getNumber()


                + "' WHERE " + Col_8 + "!='" +"0" + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }



    public Cursor updateStatus(StatusModel td) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + StatusTable
                + " SET "
                + Col_status + "='" + td.getStatus()
                + "', "
                + Col_id + "='" + td.getStatus()
                + "', "
                + Col_number + "='" + td.getNumber()



                + "' WHERE " + Col_order_id + "='" + td.getOrderId() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }

    public Cursor updateDate(DateModel td) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + DateTable
                + " SET "
                + Col_day + "='" + td.getDay()
                + "', "
                + Col_time + "='" + td.getTime()


                + "' WHERE " + Date_id + "='" + td.getId() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }




    public void deleteAllShop(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ ShopTable);
        db.close();
    }
    public void deleteAllDate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DateTable);
        db.close();

    }

    public void deleteStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ StatusTable);
        db.close();

    }

    public Cursor deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + ShopTable
                + " WHERE "
                + Col_1 + "='"
                + id + "'";
        Cursor result = db.rawQuery(query, null);
        return result;
    }


}
