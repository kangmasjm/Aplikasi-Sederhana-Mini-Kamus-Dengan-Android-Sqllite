package net.kangmasjm.kamus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DaftarKata extends Activity {
private DatabaseHelper dbhelper;
private SQLiteDatabase db = null;
private ListView listContent = null;
private static final int EDIT_ID = Menu.FIRST + 1;
private static final int DELETE_ID = Menu.FIRST + 2;
private Cursor kamusCursor = null;
CustomCursorAdapter adapter;
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
dbhelper = new DatabaseHelper(this);
setContentView(R.layout.daftarkata);
listContent = (ListView) findViewById(R.id.list1);
isDataListView();
registerForContextMenu(listContent);
}
private void isDataListView() {
try {
db = dbhelper.getWritableDatabase();
kamusCursor = db.query(“kamus”, new String[] { “_id”, “inggris”,
“indonesia”, “jerman” }, “_id>0”, null, null, null, null);
/*

Create an array to specify the fields we want to display in the
list (only the ‘inggris,indonesia,jerman’ column in this case)
*/
String[] from = new String[] { “inggris”, “indonesia”, “jerman” };
/*

and an array of the fields we want to bind those fieiplds to (in
this case just the textView ‘inggris,indonesia,jerman’ from our
new row.xml layout above)
*/
int[] to = new int[] { R.id.inggris, R.id.indonesia, R.id.jerman };
/* Now create a simple cursor adapter.. */
adapter = new CustomCursorAdapter(this, R.layout.row, kamusCursor,
from, to);
// listView.setAdapter(adapter);
listContent.setAdapter(adapter);
} catch (Exception e) {
e.printStackTrace();
} finally {
if (db != null && db.isOpen()) {
// db.close();
}
}
}
@Override
public void onDestroy() {
super.onDestroy();
try {
kamusCursor.close();
} catch (Exception e) {
}
}
@Override
public void onCreateContextMenu(ContextMenu menu, View v,
ContextMenu.ContextMenuInfo menuInfo) {
menu.add(Menu.NONE, DELETE_ID, Menu.NONE, “`Hapus”)
.setIcon(R.drawable.ic_launcher).setAlphabeticShortcut(‘e’);
menu.add(Menu.NONE, EDIT_ID, Menu.NONE, “Edit”)
.setIcon(R.drawable.ic_launcher).setAlphabeticShortcut(‘d’);
}
@Override
public boolean onContextItemSelected(MenuItem item) {
switch (item.getItemId()) {
case DELETE_ID:
AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
.getMenuInfo();
delete(info.id);
return (true);
case EDIT_ID:
AdapterView.AdapterContextMenuInfo infox = (AdapterView.AdapterContextMenuInfo) item
.getMenuInfo();
Cursor ckamusCursor = db.rawQuery(
“SELECT _ID, inggris, indonesia, jerman ”
+ “FROM kamus where _ID=” + infox.id, null);
ckamusCursor.moveToFirst();
edit(infox.id, ckamusCursor.getString(1),
ckamusCursor.getString(2), ckamusCursor.getString(3));
return (true);
}
return (super.onOptionsItemSelected(item));
}
private void edit(long id, String pinggris, String pindonesia,
String pjerman) {
LayoutInflater inflater = LayoutInflater.from(this);
View addView = inflater.inflate(R.layout.edit, null);
EditText edinggris = (EditText) addView.findViewById(R.id.inggris);
EditText edindonesia = (EditText) addView.findViewById(R.id.indonesia);
EditText edjerman = (EditText) addView.findViewById(R.id.jerman);
edinggris.setText(pinggris);
edindonesia.setText(pindonesia);
edjerman.setText(pjerman);
final DialogWrapper wrapper = new DialogWrapper(addView);
final long xid = id;
/*

wrapper.inggrisField.setText(pinggris);
wrapper.indonesiaField.setTag(pindonesia)
*/
new AlertDialog.Builder(this)
.setTitle(R.string.add_title)
.setView(addView)
.setPositiveButton(R.string.save,
new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog,
int whichButton) {
processEdit(wrapper, xid);
}
})
.setNegativeButton(R.string.cancel,
new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog,
int whichButton) {
//ignore, just dismiss
}
}).show();
}
private void delete(final long rowId) {
if (rowId > 0) {
new AlertDialog.Builder(this)
.setTitle(R.string.delete_title)
.setPositiveButton(R.string.ok,
new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog,
int whichButton) {
processDelete(rowId);
}
})
.setNegativeButton(R.string.cancel,
new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog,
int whichButton) {
// ignore, just dismiss
}
}).show();
}
}
@SuppressWarnings(“deprecation”)
private void processEdit(DialogWrapper wrapper, long id) {
ContentValues values = new ContentValues(2);
values.put(“inggris”, wrapper.getinggris());
values.put(“indonesia”,
wrapper.getindonesia());
values.put(“jerman”, wrapper.getjerman());
// db.update(table, values, whereClause, whereArgs)
db.update(“kamus”, values, “_id=” + id, null);
kamusCursor.requery () ;
}
@SuppressWarnings(“deprecation”)
private void processDelete(long rowId) {
String[] args = { String.valueOf(rowId) };
db.delete(“kamus”, “_ID=?”, args);
kamusCursor.requery () ;
}
class DialogWrapper {
EditText inggrisField = null;
EditText indonesiaField = null;
EditText jermanField = null;
View base = null;
DialogWrapper(View base) {
this.base = base;
indonesiaField = (EditText) base.findViewById(R.id.indonesia);
}
String getinggris() {
return (getinggrisField().getText().toString());
}
String getindonesia() {
return (getindonesiaField().getText().toString());
}
String getjerman() {
return (getjermanField().getText().toString());
}
private EditText getinggrisField() {
if (inggrisField == null) {
inggrisField = (EditText) base.findViewById(R.id.inggris);
}
return (inggrisField);
}
private EditText getindonesiaField() {
if (indonesiaField == null) {
indonesiaField = (EditText) base.findViewById(R.id.indonesia);
}
return (indonesiaField);
}
private EditText getjermanField() {
if (jermanField == null) {
jermanField = (EditText) base.findViewById(R.id.jerman);
}
return (jermanField);
}
}
protected class CustomCursorAdapter extends SimpleCursorAdapter {
@SuppressWarnings(“unused”)
private int layout;
private LayoutInflater inflater;
@SuppressWarnings(“unused”)
private Context context;
@SuppressWarnings(“deprecation”)
public CustomCursorAdapter(Context context, int layout, Cursor c,
String[] from, int[] to) {
super (context, layout, c, from, to);
this.layout = layout;
this.context = context;
inflater = LayoutInflater.from(context);
}
@Override
public View newView(Context context, Cursor cursor, ViewGroup parent) {
View v = inflater.inflate(R.layout.row, parent, false);
return v;
}
@Override
public void bindView(View v, Context context, Cursor c) {
// 1 is the column where you’re getting your data from
String inggris = c.getString(1);
String jerman = c.getString(3);
String indonesia = c.getString(2);
/**

Next set the name of the entry.
*/
TextView name_text = (TextView) v.findViewById(R.id.inggris);
TextView des_text = (TextView) v.findViewById(R.id.jerman);
TextView id_text = (TextView) v.findViewById(R.id.indonesia);
des_text.setText(jerman);
id_text.setText(indonesia);
if (name_text != null) {
name_text.setText(inggris);
}
}
}
}
