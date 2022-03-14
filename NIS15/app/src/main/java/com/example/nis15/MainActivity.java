package com.example.nis15;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    Integer i;
    String[] from;
    int[] to;
    static ListView listView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        try {
            dialog.setMessage(getTitle().toString() + " версия " + getPackageManager().getPackageInfo(getPackageName(),
                    0).versionName + "\r\n\nПрограмма с примером выполнения диалогового окна \r\n\nАвтор -  Шагаров Дмитрий Александрович БПИ202");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        dialog.setTitle("О программе");
        dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setIcon(R.mipmap.ic_launcher_round);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        from = new String[]{"Name"};
        to = new int[]{R.id.textView};
        Button bu = findViewById(R.id.button);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        Button btnadd = findViewById(R.id.buttonAdd);
        final EditText editadd =
                findViewById(R.id.editTextAddingARecord);
        SQLiteDatabase db =
                openOrCreateDatabase("DBName", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS MyTable5 (_id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR);");
        Cursor cursor = db.rawQuery("SELECT * FROM Mytable5",
                null);
        i = cursor.getCount() + 1;
        if (cursor.getCount() > 0) {
            MyCursorAdapter scAdapter = new
                    MyCursorAdapter(MainActivity.this, R.layout.list_item, cursor, from
                    , to);
            listView = getListView();
            listView.setAdapter(scAdapter);
        }
        db.close();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db =
                        openOrCreateDatabase("DBName", MODE_PRIVATE, null);
                Cursor cursor2 = db.rawQuery("SELECT * FROM Mytable5", null);
                i = cursor2.getCount() + 1;
//цикл для того, чтобы подбирать значения _id и не допускать
                //  одинаковых значений (primary key как-никак)
                for (int k = 1; k <= i; k++) {
                    Cursor cursor3 = db.rawQuery("SELECT * FROM Mytable5 WHERE _id=" + k + "", null);
                    if (cursor3.getCount() == 0) {
                        i = k;
                        break;
                    }
                }
                db.execSQL("INSERT INTO MyTable5 VALUES ('" + i + "','" + editadd.getText().toString() + "');");

                Cursor cursor = db.rawQuery("SELECT * FROM Mytable5", null);
                MyCursorAdapter scAdapter = new
                        MyCursorAdapter(MainActivity.this, R.layout.list_item, cursor, from
                        , to);
                listView = getListView();
                listView.setAdapter(scAdapter);
                db.close();
                Toast.makeText(getListView().getContext(), "a row added to the table", Toast.LENGTH_LONG).show();
            }
        });
    }
}


class MyCursorAdapter extends SimpleCursorAdapter {
    private int layout_;
    private Cursor cursor;
    String[] from;
    int[] to;
    ListView listView;
    EditText edit2;

    public MyCursorAdapter(Context context, int layout, Cursor
            c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        layout_ = layout;
        cursor = c;
    }

    @Override
    public void bindView(View view, Context _context, Cursor
            cursor) {
        @SuppressLint("Range") String data =
                cursor.getString(cursor.getColumnIndex("Name"));
        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
        TextView text =
                view.findViewById(R.id.textViewListItemText);
        text.setText(data);
        Button butdel = view.findViewById(R.id.buttonDelete);
        Button butedit = view.findViewById(R.id.buttonEdit);
        listView = MainActivity.listView;
        butdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db =
                        _context.openOrCreateDatabase("DBName", MODE_PRIVATE, null);
                db.execSQL("DELETE FROM MyTable5 WHERE _id=" + id + "");
                Cursor cursor = db.rawQuery("SELECT * FROM Mytable5", null);
                from = new String[]{"Name"};
                to = new int[]{R.id.textView};
                MyCursorAdapter scAdapter = new
                        MyCursorAdapter(_context, R.layout.list_item, cursor, from, to);
                listView.setAdapter(scAdapter);
                db.close();
                Toast.makeText(_context, "row deleted from the db id=" + id, Toast.LENGTH_LONG).show();
            }
        });
        butedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new
                        AlertDialog.Builder(_context);
                dialog.setMessage("Enter new value:");
                dialog.setTitle("Changing the item");
                LayoutInflater inflater = new
                        LayoutInflater(_context) {
                            @Override
                            public LayoutInflater cloneInContext(Context
                                                                         context) {
                                return null;
                            }
                        };
                View dialogview =
                        inflater.inflate(R.layout.dialog, null);
                dialog.setView(dialogview);
                edit2 =
                        dialogview.findViewById(R.id.editTextCnahgeDBRecord);
                edit2.setText(text.getText().toString());
                dialog.setNeutralButton("OK", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int i) {
                                SQLiteDatabase db =
                                        _context.openOrCreateDatabase("DBName", MODE_PRIVATE, null);
                                db.execSQL("UPDATE MyTable5 SET Name='" + edit2.getText().toString() + "' WHERE _id=" + id + "");
                                Cursor cursor = db.rawQuery("SELECT * FROM Mytable5", null);
                                from = new String[]{"Name"};
                                to = new int[]{R.id.textView};
                                MyCursorAdapter scAdapter = new
                                        MyCursorAdapter(_context, R.layout.list_item, cursor, from, to);
                                listView.setAdapter(scAdapter);
                                db.close();
                                Toast.makeText(_context, "row edited from the db row id=" + id, Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
                dialog.setIcon(R.mipmap.ic_launcher_round);
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public View newView(Context context, Cursor cursor,
                        ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout_, parent, false);
        return view;
    }
}