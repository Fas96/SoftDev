package com.fas.smash_k;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends Activity implements View.OnClickListener {

    //Changeeed
    private static final int PICK_IMAGE = 1;

    //hooks for map changing
    RadioGroup radioGroup1,radioGroup2;
    RadioButton radioButton;

    Dialog MyDialoge;
    TextView changeName,changeUsername,changeBirthday,privacy,contactus;
    Uri imageUri;
    private CircleImageView ProfileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        //hooks for different types of maps
        radioGroup1=findViewById(R.id.mapGroupButtons1);
        radioGroup2=findViewById(R.id.mapGroupButtons2);


        //hooks
        changeName=findViewById(R.id.changeName);
        changeUsername=findViewById(R.id.changeUsername);
        changeBirthday=findViewById(R.id.changeBirthday);
        privacy=findViewById(R.id.privacy);
        contactus=findViewById(R.id.contactus);
        ProfileImage=findViewById(R.id.profileimage);
        ProfileImage.setOnClickListener(this);



        //FOR DIFFERENT MAP OPTIONS
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton=findViewById(i);
                Toast.makeText(getApplicationContext(),radioButton.getText().toString()+" Button Clickeddd",Toast.LENGTH_LONG).show();
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton=findViewById(i);
                Toast.makeText(getApplicationContext(),radioButton.getText().toString()+" Button Clickeddd",Toast.LENGTH_LONG).show();
            }
        });





        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ContactUsSettings.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, PrivacySettings.class);
                SettingsActivity.this.startActivity(intent);
            }
        });
        changeName.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialoge = new Dialog(SettingsActivity.this);
                MyDialoge.setContentView(R.layout.changename);
                MyDialoge.setTitle("Change Name");


                final EditText getName = (EditText) MyDialoge.findViewById(R.id.getName);
                Button done = (Button) MyDialoge.findViewById(R.id.OK);
                MyDialoge.show();

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        if (getName.getText().toString().isEmpty()) {
                            getName.setError("Invalid");
                        } else {
                            getName.setError(null);
                            changeName.setText(getName.getText().toString());

                            MyDialoge.cancel();
                        }


                    }
                });
            }
        });


        changeUsername.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialoge = new Dialog(SettingsActivity.this);
                MyDialoge.setContentView(R.layout.changeusername);
                MyDialoge.setTitle("Change Name");


                final EditText getName = (EditText) MyDialoge.findViewById(R.id.getName);
                Button done = (Button) MyDialoge.findViewById(R.id.OK);
                MyDialoge.show();

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v12) {
                        if (getName.getText().toString().isEmpty()) {
                            getName.setError("Invalid");
                        } else {
                            changeUsername.setText(getName.getText().toString());

                            MyDialoge.cancel();
                        }


                    }
                });

            }
        });


        changeBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog picker;
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SettingsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                changeBirthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    //Changedddd
}
