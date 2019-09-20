package com.example.planner.DataBasePackage;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.planner.Task;

import java.util.ArrayList;

public class DataBaseAdapter {

    Context c;
    SQLiteDatabase db;
    DataBaseHelper helper;

    /*
    1. INITIALIZE DB HELPER AND PASS IT A CONTEXT
     */

    public DataBaseAdapter(Context c) {
        this.c = c;
        helper = new DataBaseHelper(c);
    }


    /*
    SAVE DATA TO DB
     */
    public boolean saveTask(Task task) {
        try {
            db = helper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, task.getName());
            cv.put(Constants.CATEGORY, task.getCategory());
            cv.put(Constants.MOEID,task.getId());
            long result = db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);
            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }

        return false;
    }



    /*
     1. RETRIEVE TASKS FROM DB AND POPULATE ARRAYLIST
     2. RETURN THE LIST
     */
    public ArrayList<Task> retrieveTask(String category) {
        ArrayList<Task> tasks=new ArrayList<>();

        try {
            db = helper.getWritableDatabase();


            Cursor c=db.rawQuery("SELECT * FROM "+Constants.TB_NAME+" WHERE "+Constants.CATEGORY+" = '"+category+"'",null);

            Task t;
            tasks.clear();

            while (c.moveToNext())
            {
                String s_name=c.getString(1);
                String s_category=c.getString(2);
                String s_id = c.getString(3);

                t=new Task();
                t.setName(s_name);
                t.setCategory(s_category);
                t.setId(s_id);

                tasks.add(t);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }

        return tasks;
    }


}
