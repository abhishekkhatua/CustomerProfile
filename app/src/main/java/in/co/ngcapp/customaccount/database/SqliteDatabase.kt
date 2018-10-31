package `in`.co.ngcapp.customaccount.database

import `in`.co.ngcapp.model.UserData
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SqliteDatabase(mContext: Context) : SQLiteOpenHelper(mContext, "TimeTableSheet", null, 1) {

    // Database Name
    val DATABASE_NAME = "CustomerAccount"

    // Contacts table name
    private val TABLE_CUSTOMER = "CustomerDetails"

    // Contacts Table Columns names
    private val KEY_ID = "ids"
    private val KEY_Name = "customerName"
    private val KEY_IMAGE = "customerImage"
    private val KEY_PHONENUMBER = "customerPhone"
    private val KEY_DATEOFBIRTH = "customerDateOf"
    private val KEY_ADDRESS = "customerAddress"
    private val KEY_INCOME = "customerIncome"
    private val KEY_FILE = "customerFile"

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CUSTOMER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_Name + " TEXT," + KEY_IMAGE + " TEXT," + KEY_INCOME + " TEXT,"
                + KEY_FILE + " TEXT," + KEY_DATEOFBIRTH + " TEXT," + KEY_PHONENUMBER + " TEXT," + KEY_ADDRESS + " TEXT" + ")")
        Log.i("createTable", CREATE_CONTACTS_TABLE)
        db!!.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER)
        // Create tables again
        onCreate(db);
    }

    fun addFav(
        customerName: String,
        customerImages: String,
        customerDateOfBirth: String,
        customerPhoneNumber: String,
        customerAddress: String,
        customerIncome: String,
        customerFile: String
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_Name, customerName)
        values.put(KEY_IMAGE, customerImages)
        values.put(KEY_DATEOFBIRTH, customerDateOfBirth)
        values.put(KEY_PHONENUMBER, customerPhoneNumber)
        values.put(KEY_ADDRESS, customerAddress)
        values.put(KEY_INCOME, customerIncome)
        values.put(KEY_FILE, customerFile)
        db.insert(TABLE_CUSTOMER, null, values)
        Log.i("TIMETABLE", values.toString())
        db.close()
        // Closing database connection
    }

    fun getALLDATA(): List<UserData> {
        val getAllData = ArrayList<UserData>()
        val selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val recentData = UserData(cursor.getString(0), cursor.getString(1), cursor.getString(6), cursor.getString(5), cursor.getString(7), cursor.getString(3))


                getAllData.add(recentData)
            } while (cursor.moveToNext())
        }


        return getAllData
    }

    fun deleteContact(mSectionCode: String) {
        val db = this.writableDatabase
        val DELETE_QUERY = "DELETE FROM $TABLE_CUSTOMER WHERE $KEY_ADDRESS='$mSectionCode'"
        Log.i("ATTADANCESDELETE", DELETE_QUERY)
        db.execSQL(DELETE_QUERY)
        db.close()
    }


}