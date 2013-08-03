package com.diet.WellnessSolutions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 7/18/13
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class SQLiteDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WellnessSolutions";
    private static final String TABLE_TRANSECTION = "transection";
    private static final String TABLE_RECIPE = "recipe";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TRANSECTION +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " kcal VARCHAR(255)," +
                " datetime VARCHAR(255));");
        Log.d("CREATE TABLE","Create Table Successfully.");
    }
    public long insertTransectionData(double kcal) {
        try {
            SQLiteDatabase sqLiteDatabase;
            sqLiteDatabase = this.getWritableDatabase(); // Write Data

            /**
             *  for API 11 and above
             SQLiteStatement insertCmd;
             String strSQL = "INSERT INTO " + TABLE_MEMBER
             + "(MemberID,Name,Tel) VALUES (?,?,?)";

             insertCmd = db.compileStatement(strSQL);
             insertCmd.bindString(1, strMemberID);
             insertCmd.bindString(2, strName);
             insertCmd.bindString(3, strTel);
             return insertCmd.executeInsert();
             */
            Date now = new Date();
            long timeMilliseconds = now.getTime();
            ContentValues Val = new ContentValues();
            Val.put("kcal", kcal);
            Val.put("datetime", timeMilliseconds);

            long rows = sqLiteDatabase.insert(TABLE_TRANSECTION, null, Val);

            sqLiteDatabase.close();
            return rows; // return rows inserted.

        } catch (Exception e) {
            return -1;
        }

    }
    public String[][] selectAllTransectionData() {

        try {
            String arrData[][] = null;

            SQLiteDatabase sqLiteDatabase;
            sqLiteDatabase = this.getReadableDatabase(); // Read Data

            Cursor cursor = sqLiteDatabase.query(TABLE_TRANSECTION, new String[] { "*" },
                    null,null, null, null, null, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()][cursor.getColumnCount()];
                    /***
                     *  [x][0] = MemberID
                     *  [x][1] = Name
                     *  [x][2] = Tel
                     */
                    int i= 0;
                    do {
                        arrData[i][0] = cursor.getString(0);
                        arrData[i][1] = cursor.getString(1);
                        arrData[i][2] = cursor.getString(2);
                        i++;
                    } while (cursor.moveToNext());

                }
            }
            cursor.close();
            sqLiteDatabase.close();
            return arrData;

        } catch (Exception e) {
            return null;
        }

    }
        public String[] selectAllRecipeData() {
            // TODO Auto-generated method stub

            try {
                String arrData[] = null;
                SQLiteDatabase db;
                db = this.getReadableDatabase(); // Read Data

                String strSQL = "SELECT recipe FROM " + TABLE_RECIPE;
                Cursor cursor = db.rawQuery(strSQL, null);

                if(cursor != null)
                {
                    if (cursor.moveToFirst()) {
                        arrData = new String[cursor.getCount()];
                        /***
                         *  [x] = Name
                         */
                        int i= 0;
                        do {
                            arrData[i] = cursor.getString(0);
                            i++;
                        } while (cursor.moveToNext());

                    }
                }
                cursor.close();

                return arrData;

            } catch (Exception e) {
                return null;
            }

        }
    public String[][] searchRecipeData(String search) {
        // TODO Auto-generated method stub

        try {
            String arrData[][] = null;

            SQLiteDatabase sqLiteDatabase;
            sqLiteDatabase = this.getReadableDatabase(); // Read Data

            Cursor cursor = sqLiteDatabase.query(TABLE_RECIPE, new String[] { "*" },
                    "recipe LIKE ?",new String[] { "%"+search+"%" }, null, null, null, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()][cursor.getColumnCount()];
                    int i= 0;
                    do {
                        arrData[i][0] = cursor.getString(0);
                        arrData[i][1] = cursor.getString(1);
                        arrData[i][2] = cursor.getString(2);
                        i++;
                    } while (cursor.moveToNext());

                }
            }
            cursor.close();

            return arrData;

        } catch (Exception e) {
            return null;
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSECTION);
        onCreate(sqLiteDatabase);
    }
}
