package com.fas.smash_k.ui.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fas.smash_k.R;
import com.fas.smash_k.SettingsActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private NotificationsViewModel notificationsViewModel;
    Button settingsButton;
    Button ProfileImage;
    Toolbar toolbar;
    Uri imageUri;
    int appicons[]=new int[]{R.id.appicon1,R.id.appicon2,R.id.appicon3,R.id.appicon4};
    CollapsingToolbarLayout ctoolbar;
    private  View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_notifications, container, false);

        //hooks for appicons
        for(int i=0;i<appicons.length;i++){
            ImageButton ib=root.findViewById(appicons[i]);
            ib.setOnClickListener(this);
        }

        settingsButton = root.findViewById(R.id.settings);
        ProfileImage=root.findViewById(R.id.profileimage);
        ProfileImage.setOnClickListener(this);


        settingsButton.setOnClickListener(v -> {
            Intent settingsIntent=new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingsIntent);
        });



        return root;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.appicon1:
            case R.id.appicon2:
            case R.id.appicon3:
            case R.id.appicon4:
                showCustomDialog();
                break;
            default:
                getProfileImage();

        }

        }

    public void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = root.findViewById(((ViewGroup)getView().getParent()).getId());

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.change_icon_dialogue_box, viewGroup, false);



        Button close=(Button)dialogView.findViewById(R.id.buttonOk);



        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

    }

    private void getProfileImage() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                ProfileImage.setBackground(new BitmapDrawable(getResources(),bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}