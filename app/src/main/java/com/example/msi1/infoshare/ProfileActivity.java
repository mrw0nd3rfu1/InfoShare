package com.example.msi1.infoshare;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mProfileImage = (CircleImageView) findViewById(R.id.image);
        mName = (EditText) findViewById(R.id.name);
        mPhone = (EditText) findViewById(R.id.phoneNo);
        mEmail = (EditText) findViewById(R.id.email);
        mFab = (FloatingActionButton) findViewById(R.id.profileSave);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] arr = new String[3];
                arr[0]= String.valueOf(mName.getText());
                arr[1]= String.valueOf(mPhone.getText());
                arr[2]= String.valueOf(mEmail.getText());

                writeToFile(arr,ProfileActivity.this);
            }
        });


    }

    private void writeToFile(String[] data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(String.valueOf(data));
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write      failed: " + e.toString());
            //hello
        }
    }
}
