package com.andreypshenichnyj.iate.laba_3_android.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyDbManager(context: Context) {
    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    suspend fun insertToDb(title: String, content: String, uri: String, time:String) = withContext(Dispatchers.IO) {
        val values = ContentValues().apply {
            put(ZametkiDbClass.COLUMN_NAME_TITLE, title)
            put(ZametkiDbClass.COLUMN_NAME_CONTENT, content)
            put(ZametkiDbClass.COLUMN_NAME_IMAGE_URI, uri)
            put(ZametkiDbClass.COLUMN_NAME_TIME, time)
        }
        db?.insert(ZametkiDbClass.TABLE_NAME, null, values)
    }

    suspend fun updateItem(title: String, content: String, uri:String, id: Int, time: String) = withContext(Dispatchers.IO){
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(ZametkiDbClass.COLUMN_NAME_TITLE, title)
            put(ZametkiDbClass.COLUMN_NAME_CONTENT, content)
            put(ZametkiDbClass.COLUMN_NAME_IMAGE_URI, uri)
            put(ZametkiDbClass.COLUMN_NAME_TIME, time)

        }
        db?.update(ZametkiDbClass.TABLE_NAME, values, selection, null)
    }

    fun removeItemFromDb(id: String) {
        val selection = BaseColumns._ID + "=$id"
        db?.delete(ZametkiDbClass.TABLE_NAME, selection, null)
    }

    suspend fun readDbData(search: String): ArrayList<ListItem> = withContext(Dispatchers.IO) {
        val dataList = ArrayList<ListItem>()
        val selection = "${ZametkiDbClass.COLUMN_NAME_TITLE} like ?"
        val cursor = db?.query(
            ZametkiDbClass.TABLE_NAME,
            null,
            selection,
            arrayOf("%$search%"),
            null,
            null,
            null
        )

        while (cursor?.moveToNext()!!) {
            val title = cursor.getColumnIndex(ZametkiDbClass.COLUMN_NAME_TITLE)
            val desc = cursor.getColumnIndex(ZametkiDbClass.COLUMN_NAME_CONTENT)
            val uri = cursor.getColumnIndex(ZametkiDbClass.COLUMN_NAME_IMAGE_URI)
            val time = cursor.getColumnIndex(ZametkiDbClass.COLUMN_NAME_TIME)
            val id = cursor.getColumnIndex(BaseColumns._ID)

            val dataId = cursor.getInt(id)
            val dataTitle = cursor.getString(title)
            val dataUri = cursor.getString(uri)
            val dataTime = cursor.getString(time)
            val dataDesc = cursor.getString(desc)

            val item = ListItem()
            item.title = dataTitle.toString()
            item.desc = dataDesc.toString()
            item.uri = dataUri.toString()
            item.time = dataTime.toString()
            item.id = dataId

            dataList.add(item)
        }
        cursor.close()

        return@withContext dataList
    }

    fun closeDb() {
        myDbHelper.close()
    }
}