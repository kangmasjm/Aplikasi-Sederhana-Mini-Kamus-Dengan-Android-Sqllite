package net.kangmasjm.kamus;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class TambahKata extends Activity{
private SQLiteDatabase db = null;
private EditText txtInggris;
private EditText txtIndonesia;
private EditText txtJerman;
private DatabaseHelper datakamus = null;
public static final String INGGRIS= “inggris”;
public static final String INDONESIA = “indonesia”;
public static final String JERMAN = “JERMAN”;
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
datakamus = new DatabaseHelper(this);
db = datakamus.getWritableDatabase();
setContentView(R.layout.tambahkata);
txtInggris = (EditText) findViewById(R.id.txtInggris);
txtIndonesia = (EditText) findViewById(R.id.txtIndonesia);
txtJerman = (EditText)
findViewById(R.id.txtJerman);
}
public void saveData(View view) {
String bhsindonesia = txtIndonesia.getText().toString();;
String bhsjerman= txtJerman.getText().toString();
String englishword = txtInggris.getText().toString();
ContentValues cv=new ContentValues();
cv.put(INGGRIS, englishword);
cv.put(INDONESIA, bhsindonesia);
cv.put(JERMAN, bhsjerman);
if (db.insert(“kamus”, INGGRIS, cv)>0){
Toast.makeText(getBaseContext(), “Save Data Success”, Toast.LENGTH_SHORT).show();
}else{
Toast.makeText(getBaseContext(), “Save Data Fail”, Toast.LENGTH_SHORT).show();
}
}
@Override
public void onDestroy() {
super.onDestroy();
db.close();
}
}
