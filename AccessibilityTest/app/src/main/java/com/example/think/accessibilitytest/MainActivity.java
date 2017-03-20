package com.example.think.accessibilitytest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    boolean firstIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstIn = false;

        this.findViewById(R.id.activeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(startIntent);
            }
        });

        this.findViewById(R.id.installButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAccessibilityService.INVOKE_TYPE = MyAccessibilityService.INSTALL_TYPE;
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.apk";
                File installFile = new File(fileName);
                if (installFile.exists())
                    installFile.delete();
                try{
                    installFile.createNewFile();
                    FileOutputStream out = new FileOutputStream(installFile);
                    byte[] buffer = new byte[512];
                    InputStream in = MainActivity.this.getAssets().open("test.apk");
                    int count;
                    while ((count = in.read(buffer)) != -1){
                        out.write(buffer, 0, count);
                    }
                    in.close();


                    out.close();
                } catch (IOException e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                startActivity(intent);
            }
        });

        this.findViewById(R.id.uninstallButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAccessibilityService.INVOKE_TYPE = MyAccessibilityService.UNINSTALL_TYPE;
                Uri packageUri = Uri.parse("package:com.example.think.circletest");
                Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
                startActivity(intent);
            }
        });

        this.findViewById(R.id.killAppButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAccessibilityService.INVOKE_TYPE = MyAccessibilityService.KILL_TYPE;
                Uri packageUri = Uri.parse("package:com.example.think.circletest");
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(packageUri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstIn)
            MyAccessibilityService.reset();
    }
}
