package com.iamdeveloper.applicationpermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    CoordinatorLayout coordinator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinator = (CoordinatorLayout) findViewById(R.id.coordinator);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= 23 ){
                    Toast.makeText(view.getContext(),""+Build.VERSION.SDK_INT,Toast.LENGTH_LONG).show();
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        }
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                            {Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSION_READ_EXTERNAL_STORAGE);
                        return;
                    }
                    getExternal();
                }else{
                    Toast.makeText(view.getContext(),""+Build.VERSION.SDK_INT,Toast.LENGTH_LONG).show();
                    getExternal();
                }

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Toast.makeText(this,""+requestCode,Toast.LENGTH_LONG).show();
        switch (requestCode) {
            case MY_PERMISSION_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getExternal();
                }else{

                    Snackbar.make(coordinator,"คุณต้องทำการอนุญาตให้เขาถึงไฟล์",Snackbar.LENGTH_INDEFINITE)
                            .setAction("อนุญาต", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent();
                                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    i.setData(Uri.parse("package:" + getPackageName()));
                                    i.addCategory(Intent.CATEGORY_DEFAULT);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(i);
                                }
                            }).show();

                }

            }
        }
    }


    private void getExternal(){
        Toast.makeText(this,"เลือกข้อมูล",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
