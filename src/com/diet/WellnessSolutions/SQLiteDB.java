package com.diet.WellnessSolutions;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.*;
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
    private Context mCtx;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mCtx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    public long insertTransectionData(double kcal,String getrecipe) {
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
            Val.put("recipe",getrecipe);
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
    public String[][] listTransectionData() {
        // TODO Auto-generated method stub

        try {
            String arrData[][] = null;

            SQLiteDatabase sqLiteDatabase;
            sqLiteDatabase = this.getReadableDatabase(); // Read Data

            //Cursor cursor = sqLiteDatabase.query(TABLE_TRANSECTION, new String[] { "*" },
            //        null,null, null, null, null, null);
            String strSQL = "SELECT * FROM transection WHERE (strftime('%d','now')-strftime('%d', datetime / 1000, 'unixepoch'))<=30 ORDER BY ID DESC";
            Cursor cursor = sqLiteDatabase.rawQuery(strSQL, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getCount()][cursor.getColumnCount()];
                    int i= 0;
                    do {
                        arrData[i][0] = cursor.getString(0);
                        arrData[i][1] = cursor.getString(1);
                        arrData[i][2] = cursor.getString(2);
                        arrData[i][3] = cursor.getString(3);
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
    public void createDataBase(){
        boolean dbExist = checkDataBase();
        //boolean dbExist = false;
        if (dbExist) {
            // do nothing - database already exist

        } else {

            //this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    // this method will check the existance of database
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        String myPath = "data/data/com.diet.WellnessSolutions/databases/WellnessSolutions";
        try {
            checkDB = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {

        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    // copy the database file from asset folder to the DDMS database folder
    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        AssetManager assetManager = mCtx.getAssets();
        InputStream myInput = assetManager.open("WellnessSolutions");

        // Path to the just created empty db
        String outFileName = "data/data/com.diet.WellnessSolutions/databases/WellnessSolutions";
        new File(outFileName).getParentFile().mkdir();
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

}
