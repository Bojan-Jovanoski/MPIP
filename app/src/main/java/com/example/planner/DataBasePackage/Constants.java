package com.example.planner.DataBasePackage;

public class Constants {
    /*
  COLUMNS
   */
    static final String ROW_ID="id";
    static final String NAME="name";
    static final String CATEGORY="category";
    static final String MOEID="moeid";



    /*
    DB PROPERTIES
     */
    static final String DB_NAME="lv_DB";
    static final String TB_NAME="lv_TB";
    static final int DB_VERSION=1;

    /*
    TABLE CREATION STATEMENT
     */
    static final String CREATE_TB="CREATE TABLE lv_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,category TEXT NOT NULL,moeid TEXT NOT NULL);";


    /*
    TABLE DELETION STMT
     */
    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;

}
