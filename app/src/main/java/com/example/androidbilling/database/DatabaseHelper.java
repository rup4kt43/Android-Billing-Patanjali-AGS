package com.example.androidbilling.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidbilling.sales_menu_components.categorySelection.dto.VerticalDTO;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.ItemListItem;
import com.example.androidbilling.utilities.global.GlobalFunctions;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "androidBilling.db";
    private static final String TABLE_CATEGORY = "category_list";
    private static final String TABLE_MENU_ITEM = "ITEM_TABLE";
    private static final String COL_ID = "id";
    private static final String COL_VERTICAL_NAME = "vertical";
    private static final String COL_MCODE = "mcode";
    private static final String COL_DESCA = "desca";
    private static final String COL_BARCODE = "barcode";
    private static final String COL_BRAND = "brand";
    private static final String COL_DIVISION = "division";
    //private static final String COL_MENUCODE = "menucode";
    // private static final String COL_TYPE = "type";
    //private static final String COL_BASEUNIT = "baseunit";
    // private static final String COL_RATE_A = "rateA";
    // private static final String COL_PRATE_A = "prateA";
    //private static final String COL_QUANTITY = "quantity";
    private static final String IS_CHECKED = "isVerticalChecked";

    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COL_VERTICAL_NAME + " TEXT, " + IS_CHECKED + " INTEGER DEFAULT 1 )";

    public static final String CREATE_TABLE_MENU_ITEM = "CREATE TABLE " +
            TABLE_MENU_ITEM + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COL_VERTICAL_NAME + " TEXT, " +
            COL_MCODE + " TEXT, " + COL_BRAND + " TEXT, " + COL_DESCA + " TEXT, " + COL_BARCODE + " TEXT, " +
            COL_DIVISION + " TEXT, " + IS_CHECKED + " INTEGER DEFAULT 1 )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_MENU_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU_ITEM);
        onCreate(db);
    }


    //---------------------INSERTING INTO DATABASE------------------------------------------------//
    public long insertVertical(List<String> category) {
        long result = 0;
        clearVertical();
        for (int i = 0; i < category.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_VERTICAL_NAME, category.get(i));
            result = db.insert(TABLE_CATEGORY, null, cv);

        }
        return result;
    }


    public long insertMenuItem(List<ItemListItem> itemList) {
        long result = 0;
        clearMenuItem();
        for (int i = 0; i < itemList.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_MCODE, itemList.get(i).getMCODE());
            cv.put(COL_DIVISION, itemList.get(i).getDIVISIONS());
            cv.put(COL_VERTICAL_NAME, itemList.get(i).getVERTICAL());
            cv.put(COL_BRAND, itemList.get(i).getBRAND());
            cv.put(COL_DESCA, itemList.get(i).getDESCA());
            cv.put(COL_BARCODE, itemList.get(i).getBARCODE());

            result = db.insert(TABLE_MENU_ITEM, null, cv);
        }
        return result;

    }


    //----------------Retriving FROM DATABASE---------------------------//
    public List<VerticalDTO> retriveVertical() {
        List<VerticalDTO> vertical_name = new ArrayList<>();
        try {


            String query = "SELECT  " + COL_VERTICAL_NAME + "," + IS_CHECKED + " FROM " + TABLE_CATEGORY;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    VerticalDTO verticalDTO = new VerticalDTO();
                    verticalDTO.setVerticalName(cursor.getString(cursor.getColumnIndex(COL_VERTICAL_NAME)));
                    int status = (int) cursor.getLong(cursor.getColumnIndex(IS_CHECKED));
                    verticalDTO.setVerticalCheckedStatus(status);
                    vertical_name.add(verticalDTO);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            GlobalFunctions.showToast(e.toString());
        }
        return vertical_name;


    }


    //---------------------------------------------------------------------//


    //---------------------Clearing Table---------------------------------------//
    public void clearVertical() {
        db.execSQL("delete from " + TABLE_CATEGORY);
    }

    public void clearMenuItem() {
        db.execSQL("delete from " + TABLE_MENU_ITEM);
    }

    public List<ItemListItem> retriveAllItems() {

        List<ItemListItem> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MENU_ITEM + " WHERE " + IS_CHECKED + " = " + 1;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                ItemListItem itemListItem = new ItemListItem();
                itemListItem.setbARCODE(cursor.getString(cursor.getColumnIndex(COL_BARCODE)));
                itemListItem.setbRAND(cursor.getString(cursor.getColumnIndex(COL_BRAND)));
                itemListItem.setdESCA(cursor.getString(cursor.getColumnIndex(COL_DESCA)));
                itemListItem.setdIVISIONS(cursor.getString(cursor.getColumnIndex(COL_DIVISION)));
                itemListItem.setmCODE(cursor.getString(cursor.getColumnIndex(COL_MCODE)));
                itemListItem.setvERTICAL(cursor.getString(cursor.getColumnIndex(COL_VERTICAL_NAME)));

                list.add(itemListItem);


            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public List<ItemListItem> retriveItemFromCategory(String category) {

        List<ItemListItem> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MENU_ITEM + " WHERE " + COL_VERTICAL_NAME + " = '" + category + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                ItemListItem itemListItem = new ItemListItem();
                itemListItem.setbARCODE(cursor.getString(cursor.getColumnIndex(COL_BARCODE)));
                itemListItem.setbRAND(cursor.getString(cursor.getColumnIndex(COL_BRAND)));
                itemListItem.setdESCA(cursor.getString(cursor.getColumnIndex(COL_DESCA)));
                itemListItem.setdIVISIONS(cursor.getString(cursor.getColumnIndex(COL_DIVISION)));
                itemListItem.setmCODE(cursor.getString(cursor.getColumnIndex(COL_MCODE)));
                itemListItem.setvERTICAL(cursor.getString(cursor.getColumnIndex(COL_VERTICAL_NAME)));

                list.add(itemListItem);


            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public void updateCategoryStatus(String categoryName, int status) {
        if (categoryName == "") {
            String query = "UPDATE " + TABLE_CATEGORY + " SET " + IS_CHECKED + " = " + status;
            String query2 = "UPDATE " + TABLE_MENU_ITEM + " SET " + IS_CHECKED + " = " + status;

            try {
                db.execSQL(query);
                db.execSQL(query2);
            } catch (Exception e) {
                GlobalFunctions.showToast(e.toString());
            }

        } else {
            String query = "UPDATE " + TABLE_CATEGORY + " SET " + IS_CHECKED + " = " + status + " WHERE " + COL_VERTICAL_NAME + " ='" + categoryName + "'";
            String query2 = "UPDATE " + TABLE_MENU_ITEM + " SET " + IS_CHECKED + " = " + status + " WHERE " + COL_VERTICAL_NAME + " ='" + categoryName + "'";
            try {
                db.execSQL(query);
                db.execSQL(query2);
            } catch (Exception e) {
                GlobalFunctions.showToast(e.toString());
            }
        }
    }

    public List<String> searchItemWithBarcode(String result) {
        List<String> mcodeList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MENU_ITEM + " WHERE " + COL_BARCODE + "= '" + result + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String mcode = cursor.getString(cursor.getColumnIndex(COL_MCODE));
                mcodeList.add(mcode);
            } while (cursor.moveToNext());
        }

        return mcodeList;

    }
    //--------------------------------------------------------------------------//
}
