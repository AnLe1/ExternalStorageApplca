package com.basiclca.app.externalstorageapplca;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnSaveData, btnReadData;
    private TextView tvData;

    private final String fileName = "lecongan.com";
    private final String content = "Co gang len nao nguoi anh em";
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSaveData = (Button) findViewById(R.id.btn_save_data);
        btnReadData = (Button) findViewById(R.id.btn_read_data);
        tvData = (TextView) findViewById(R.id.tv_data);
        btnSaveData.setOnClickListener(this);
        btnReadData.setOnClickListener(this);
        checkAndRequestPermissions();
    }

    //Kiem tra sdcard or bo nho may co null hay khong
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    //check permission android runtime 6,0
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save_data:
                //// TODO: 15/03/2018
                savedData();
                break;
            case R.id.btn_read_data:
                //// TODO: 15/03/2018
                readData();
                break;
        }
    }
    public void savedData(){
        if(isExternalStorageReadable()){
            FileOutputStream fileOutputStream = null;
            File file;
            file = new File(Environment.getExternalStorageDirectory(),fileName);
            try {
                fileOutputStream = new FileOutputStream(file);
//            Log.d(TAG, "savedData: "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY.).getAbsolutePath());
                fileOutputStream.write(content.getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "not enough data", Toast.LENGTH_SHORT).show();
        }

    }
    public void readData(){
        BufferedReader br = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), fileName);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine() )!= null){
                buffer.append(line);
            }
            tvData.setText(buffer.toString());
            Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
