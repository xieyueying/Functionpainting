package com.example.functionpainting;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DrawView drawView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"最小分度值");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                ScaleDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button draw = (Button) findViewById(R.id.draw);
        EditText string = (EditText) findViewById(R.id.string);
        drawView = (DrawView) findViewById(R.id.drawView);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*int[] location = new int[2];
                location[0] = drawView.getWidth();
                location[1] = drawView.getHeight();
                drawView.setLocation(location);*/
                drawView.initFunction(string.getText().toString());
            }
        });
    }
    //创建最小分度值对话框
    private void ScaleDialog() {
        final LinearLayout  linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.minscalevalue_main, null);
        new AlertDialog.Builder(this)
                .setTitle("新增单词")
                .setView(linearLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText scale = (EditText) linearLayout.findViewById(R.id.MinScale);
                        drawView.initScale(Integer.valueOf(scale.getText().toString()));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
    }
}