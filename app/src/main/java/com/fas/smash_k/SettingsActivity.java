package com.fas.smash_k;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends Activity implements View.OnClickListener {

    //Changeeed
    private static final int PICK_IMAGE = 1;

    Dialog MyDialoge;
    TextView changeName,changeUsername,changeBirthday;
    Uri imageUri;
    private CircleImageView ProfileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        changeName=findViewById(R.id.changeName);
        changeUsername=findViewById(R.id.changeUsername);
        changeBirthday=findViewById(R.id.changeBirthday);
        ProfileImage=findViewById(R.id.profileimage);
        ProfileImage.setOnClickListener(this);

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialoge=new Dialog(SettingsActivity.this);
                MyDialoge.setContentView(R.layout.changename);
                MyDialoge.setTitle("Change Name");


                final EditText getName=(EditText)MyDialoge.findViewById(R.id.getName);
                Button done=(Button)MyDialoge.findViewById(R.id.OK);
                MyDialoge.show();

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeName.setText(getName.getText().toString());

                        MyDialoge.cancel();

                    }
                });
            }
        });


        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialoge=new Dialog(SettingsActivity.this);
                MyDialoge.setContentView(R.layout.changeusername);
                MyDialoge.setTitle("Change Name");


                final EditText getName=(EditText)MyDialoge.findViewById(R.id.getName);
                Button done=(Button)MyDialoge.findViewById(R.id.OK);
                MyDialoge.show();

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeUsername.setText(getName.getText().toString());

                        MyDialoge.cancel();

                    }
                });

            }
        });


        changeBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDialoge=new Dialog(SettingsActivity.this);
                MyDialoge.setContentView(R.layout.changebirthday);
                MyDialoge.setTitle("Change Name");


                final EditText getName=(EditText)MyDialoge.findViewById(R.id.getName);
                Button done=(Button)MyDialoge.findViewById(R.id.OK);
                MyDialoge.show();

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeBirthday.setText(getName.getText().toString());

                        MyDialoge.cancel();

                    }
                });

            }
        });



        // Hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
// Show status bar
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {

        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

    }

    //Changedddd
}
