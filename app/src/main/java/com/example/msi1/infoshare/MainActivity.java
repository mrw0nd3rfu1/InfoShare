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

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mListView;

    private CircleImageView mProfileImage;
    private TextView mNameText;
    private TextView mPhoneText;
    private TextView mMailText;
    private TextView mProfile;

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (RelativeLayout) findViewById(R.id.ListLayout);
        mProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        mNameText = (TextView) findViewById(R.id.profile_name);
        mPhoneText = (TextView) findViewById(R.id.profile_no);
        mMailText = (TextView) findViewById(R.id.profile_email);
        mProfile = (TextView) findViewById(R.id.profile);

        myDb = new DatabaseHelper(this);

        mListView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                Intent sharedIntent = new Intent(MainActivity.this , SharedActivity.class);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View,String>(mProfileImage,"imageTransition");
                pairs[1] = new Pair<View,String>(mNameText,"nameTransition");
                pairs[2] = new Pair<View,String>(mPhoneText,"numTransition");
                pairs[3] = new Pair<View,String>(mMailText,"mailTransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this ,pairs);
                startActivity(sharedIntent, options.toBundle());

            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharedIntent = new Intent(MainActivity.this , ProfileActivity.class);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View,String>(mProfileImage,"imageTransition");
                pairs[1] = new Pair<View,String>(mNameText,"nameTransition");
                pairs[2] = new Pair<View,String>(mPhoneText,"numTransition");
                pairs[3] = new Pair<View,String>(mMailText,"mailTransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this ,pairs);
                startActivity(sharedIntent, options.toBundle());
            }
        });

        Cursor res = myDb.getAllData();

        if (res.getCount()!= 0){
            res.moveToFirst();
            mNameText.setText(res.getString(1));
            mPhoneText.setText(res.getString(2));
            mMailText.setText(res.getString(3));
        }
    }
}
