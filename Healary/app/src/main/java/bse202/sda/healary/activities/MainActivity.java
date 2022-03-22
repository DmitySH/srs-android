package bse202.sda.healary.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import bse202.sda.healary.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
//            Intent intent = new Intent(MainActivity.this, SettingsActivity.class); startActivity(intent);
        } else if (item.getItemId() == R.id.author) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            try {
                dialog.setMessage(getTitle().toString() + " версия " + getPackageManager().getPackageInfo(getPackageName(),
                        0).versionName + "\r\n\nПрограмма для напоминании приема медицинских препаратов" +
                        " \r\n\nАвтор - Шагаров Дмитрий Александрович БПИ202");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            dialog.setTitle("О программе");
            dialog.setNeutralButton("ОК", (dialog1, which) -> dialog1.dismiss());
            dialog.setIcon(R.drawable.ic_alarm_black_24dp);
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
