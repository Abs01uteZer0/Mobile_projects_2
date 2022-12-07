package com.andreypshenichnyj.iate.laba_3_android.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context) : SQLiteOpenHelper(
    context, ZametkiDbClass.DATABASE_NAME,
    null, ZametkiDbClass.DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ZametkiDbClass.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(ZametkiDbClass.DROP_TABLE)
        onCreate(db)
    }
}
