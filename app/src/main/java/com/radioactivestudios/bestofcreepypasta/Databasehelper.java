package com.radioactivestudios.bestofcreepypasta;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by radioactive on 4/23/2017.
 */

public class Databasehelper extends SQLiteOpenHelper {

    private static String Db_path = "data/data/com.radioactivestudios.bestofcreepypasta/databases";
    private static String Db_Name = "CreepyPastaCollection3.db";
    private SQLiteDatabase CreepYPastaDatabase;
    private final Context DbContext;

    public Databasehelper(Context context) {
        super(context, Db_Name, null, 1);
        this.DbContext = context;

    }

    public void createDatabase() throws IOException {
        boolean db_exist = checkdatabse();
        if (db_exist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Cought exception while copying");


            }
        }

    }

    public boolean checkdatabse() {

        SQLiteDatabase checkdb = null;
        try {
            String Path = Db_path + Db_Name;
            checkdb = SQLiteDatabase.openDatabase(Path, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

        }
        if (checkdb != null) {
            checkdb.close();
        }
        return checkdb != null ? true : false;
    }

    private void copyDatabase() throws IOException{
        InputStream creepyInputStream = DbContext.getAssets().open(Db_Name);
        String outfilename = Db_path+Db_Name;
        OutputStream creepyOutPut =  new FileOutputStream(outfilename);
        byte[] buffer = new byte[1024];
        int lenght;
        while((lenght=creepyInputStream.read(buffer))>0){
            creepyOutPut.write(buffer,0,lenght);

        }
        creepyOutPut.flush();
        creepyOutPut.close();
        creepyInputStream.close();
    }

    public String openDatabase()  {
        String Inet = "0";
        int intet =0;
        String mypath = Db_path + Db_Name;
        CreepYPastaDatabase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        try {
            Cursor cursor = CreepYPastaDatabase.rawQuery("Select * from CreepyPastaCollection", null);
            if (cursor.moveToFirst()) {
               while(!cursor.isAfterLast()){

                    Inet = cursor.getString(cursor.getColumnIndex("Title"));
                    intet++;
                    //System.out.println("The main story is "+cursor.getString(cursor.getColumnIndex("Title")));

                    //   Toast.makeText(Databasehelper.this,"HI",Toast.LENGTH_SHORT).show();
                    //Log.e("Demodd"," "+Inet);
                   cursor.moveToNext();
                }
            }
            cursor.close();
        }
            // return contact list
            //return contactList;          }
        catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
        return Inet;
    }
    @Override
    public synchronized void close() {

        if(CreepYPastaDatabase != null)
            CreepYPastaDatabase.close();

        super.close();

    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
