package net.kangmasjm.kamus;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
private SQLiteDatabase db = null;
private Cursor kamusCursor = null;
private EditText txtInggris;
private EditText txtIndonesia;
private EditText txtJerman;
private DatabaseHelper datakamus = null;
public static final String INGGRIS = “inggris”;
public static final String INDONESIA = “indonesia”;
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
datakamus = new DatabaseHelper(this);
db = datakamus.getWritableDatabase();
setContentView(R.layout.activity_main);
txtInggris = (EditText) findViewById(R.id.txtInggris);
txtIndonesia = (EditText) findViewById(R.id.txtIndonesia);
txtJerman = (EditText) findViewById(R.id.txtJerman);
}
public void getTerjemahan(View view) {
String bhsindonesia = “”;
String bhsjerman=””;
String englishword = txtInggris.getText().toString();
kamusCursor = db.rawQuery(“SELECT _ID, INGGRIS, INDONESIA, JERMAN ”
+ “FROM kamus where INGGRIS='” + englishword
+ “‘ ORDER BY INGGRIS”, null);
if (kamusCursor.moveToFirst()) {
for (; !kamusCursor.isAfterLast();
kamusCursor.moveToNext()) {
bhsindonesia = kamusCursor.getString(2);
bhsjerman = kamusCursor.getString(3);
}
}else{
Toast.makeText(getBaseContext(), “Terjemahan Tidak ditemukan”, Toast.LENGTH_SHORT).show();
}
txtIndonesia.setText(bhsindonesia);
txtJerman.setText(bhsjerman);
}
@Override
public void onDestroy() {
super.onDestroy();
try {
kamusCursor.close();
db.close();
}catch (Exception e){
}
}
}
