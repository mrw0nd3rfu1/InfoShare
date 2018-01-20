package com.example.msi1.infoshare;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SharedActivity extends AppCompatActivity {


    private CircleImageView mProfileImage;
    private TextView mNameText;
    private TextView mPhoneText;
    private TextView mMailText;

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        mProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        mNameText = (TextView) findViewById(R.id.textView);
        mPhoneText = (TextView) findViewById(R.id.textView4);
        mMailText = (TextView) findViewById(R.id.textView5);

        myDb = new DatabaseHelper(this);

        Cursor res = myDb.getAllData();

        if (res.getCount()!= 0){
            res.moveToFirst();
            mNameText.setText(res.getString(1));
            mPhoneText.setText(res.getString(2));
            mMailText.setText(res.getString(3));
        }
    }
}
