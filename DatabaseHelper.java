package net.kangmasjm.kamus;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
private static final String DATABASE_NAME = “dbkamus”;
public static final String INGGRIS= “inggris”;
public static final String INDONESIA = “indonesia”;
public static final String JERMAN = “JERMAN”;
//Constructor DataKamus untuk initiate database
public DatabaseHelper(Context context) {
super(context, DATABASE_NAME, null, 1);
}
//method createTable untuk membuat table kamus
public void createTable(SQLiteDatabase db){
db.execSQL(“DROP TABLE IF EXISTS kamus”);
db.execSQL(“CREATE TABLE if not exists kamus (_id INTEGER PRIMARY KEY AUTOINCREMENT, ” +
“inggris TEXT, indonesia TEXT, jerman TEXT);”);
}
//method generateData untuk mengisikan data ke kamus.
public void generateData(SQLiteDatabase db){
ContentValues cv=new ContentValues();
cv.put(INGGRIS, “run”);
cv.put(INDONESIA, “lari”);
cv.put(JERMAN, “laufen”);
db.insert(“kamus”, INGGRIS, cv);
cv.put(INGGRIS, “walk”);
cv.put(INDONESIA, “jalan”);
cv.put(JERMAN, “gehen”);
db.insert(“kamus”, INDONESIA, cv);
cv.put(INGGRIS, “read”);
cv.put(INDONESIA, “membaca”);
cv.put(JERMAN, “lesen”);
db.insert(“kamus”, JERMAN, cv);
}
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
createTable(db);
generateData(db);
}
@Override
public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
//Toast.makeText(getBaseContext(), “Oncreate”, Toast.LENGTH_SHORT).show();
createTable(db);
generateData(db);
}
}
