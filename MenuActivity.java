package net.kangmasjm.kamus;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MenuActivity extends ListActivity {
String[] menuutama = new String[] { “Terjemah Kata”, “Tambah Kata”,
“Daftar Kata”, “Keluar Aplikasi”,”Developer”,”Mini Kamus by JM”};
public void onCreate(Bundle icicle) {
super.onCreate(icicle);
// Menset nilai array ke dalam list adapater sehingga data pada array
// akan dimunculkan dalam list
this.setListAdapter(new ArrayAdapter<String>(this,
android.R.layout.simple_list_item_1, menuutama));
}
@Override
/**method ini akan mengoveride method onListItemClick yang ada pada class List Activity

method ini akan d1ipanggil apabilai ada salah satu item dari list menu yang dipilih
*/
protected void onListItemClick(ListView l, View v, int position, long id) {
super.onListItemClick(l, v, position, id);
// Menangkap nilai text yang dklik
Object o = this.getListAdapter().getItem(position);
String pilihan = o.toString();
// Menampilkan hasil pilihan menu dalam bentuk Toast
tampilkanPilihan(pilihan);
}
protected void tampilkanPilihan(String pilihan) {
try {
//Intent digunakan untuk sebagai pengenal suatu activity
Intent i = null;
if (pilihan.equals(“Terjemah Kata”)) {
i = new Intent(this,MainActivity.class);
} else if (pilihan.equals(“Tambah Kata”)) {
i = new Intent(this, TambahKata.class);
} else if (pilihan.equals(“Daftar Kata”)) {
i = new Intent(this, DaftarKata.class);
} else if (pilihan.equals(“Keluar Aplikasi”)) {
finish();
} else {
Toast.makeText(this,”by Juliana Mansur”, Toast.LENGTH_LONG).show();
}
startActivity(i);
} catch (Exception e) {
e.printStackTrace();
}
}
}
