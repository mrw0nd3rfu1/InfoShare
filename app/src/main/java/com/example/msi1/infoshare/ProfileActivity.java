package com.example.msi1.infoshare;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView mProfileImage;
    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private FloatingActionButton mFab;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mProfileImage = (CircleImageView) findViewById(R.id.image);
        mName = (EditText) findViewById(R.id.name);
        mPhone = (EditText) findViewById(R.id.phoneNo);
        mEmail = (EditText) findViewById(R.id.email);
        mFab = (FloatingActionButton) findViewById(R.id.profileSave);

        myDb = new DatabaseHelper(this);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res = myDb.getAllData();
                if (res.getCount() == 0){
                boolean isInserted = myDb.insertData(mName.getText().toString(), mPhone.getText().toString(), mEmail.getText().toString(), "1");
                if (isInserted == true)
                    Toast.makeText(ProfileActivity.this,"Profile Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ProfileActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
                else {
                    boolean isUpdate = myDb.updateData("1",mName.getText().toString(), mPhone.getText().toString(), mEmail.getText().toString(), "1");
                    if (isUpdate == true)
                        Toast.makeText(ProfileActivity.this,"Profile Updated",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(ProfileActivity.this,"Error",Toast.LENGTH_LONG).show();

                }
            }
        });


    }


}
