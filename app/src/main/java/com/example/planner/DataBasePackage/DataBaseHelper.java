package com.example.planner.DataBasePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.planner.Task;

public class DataBaseHelper  extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }
    /*
    CREATE TABLE
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(Constants.CREATE_TB);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    /*
    UPGRADE TABLE
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL(Constants.DROP_TB);
            db.execSQL(Constants.CREATE_TB);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TB_NAME, Constants.NAME+ " =? AND " + Constants.CATEGORY+ " =? AND "+ Constants.MOEID+ " =?",
                new String[]{String.valueOf(task.getName()),String.valueOf(task.getCategory()),String.valueOf(task.getId())});
        db.close();
    }


}