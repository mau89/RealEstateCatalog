package com.example.realestatecatalog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private int permissionRequestCode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int locPermission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&

                (locPermission1 != PackageManager.PERMISSION_GRANTED)
        ) {
            final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, permissionRequestCode);
        }

        setToolbar();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new LoginFragment())
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Спасибо!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Извините, без данного разрещения связь с разработчиком невозможна",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new LoginFragment())
                    .commit();
            return true;
        } else if (id == R.id.action_add_property) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new AddFragment())
                    .addToBackStack(MainFragment.class.getName())
                    .commit();
            return true;
        } else if (id == R.id.action_edit_property) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new EditFragment())
                    .addToBackStack(MainFragment.class.getName())
                    .commit();
            return true;


        }
        return super.onOptionsItemSelected(item);
    }
}

