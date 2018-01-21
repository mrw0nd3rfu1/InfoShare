package com.example.msi1.infoshare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView mProfileImage;
    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private FloatingActionButton mFab;

    final int REQUEST_CODE_GALLERY = 999;

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

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        ProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res = myDb.getAllData();
                if (res.getCount() == 0){
                boolean isInserted = myDb.insertData(mName.getText().toString(), mPhone.getText().toString(), mEmail.getText().toString(), imageViewToByte(mProfileImage));
                if (isInserted == true)
                    Toast.makeText(ProfileActivity.this,"Profile Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ProfileActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
                else {
                    boolean isUpdate = myDb.updateData("1",mName.getText().toString(), mPhone.getText().toString(), mEmail.getText().toString(), imageViewToByte(mProfileImage));
                    if (isUpdate == true)
                        Toast.makeText(ProfileActivity.this,"Profile Updated",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(ProfileActivity.this,"Error",Toast.LENGTH_LONG).show();

                }
            }
        });


    }


    public static byte[] imageViewToByte(CircleImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri mImageUri = result.getUri();



                try {
                    InputStream inputStream = getContentResolver().openInputStream(mImageUri);

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mProfileImage.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


}
