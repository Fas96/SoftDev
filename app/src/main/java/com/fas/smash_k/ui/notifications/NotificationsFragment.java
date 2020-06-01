package com.fas.smash_k.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fas.smash_k.R;
import com.fas.smash_k.SettingsActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    Button settingsButton;
    Button profilePictureBtn;
    Toolbar toolbar;
    CollapsingToolbarLayout ctoolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        profilePictureBtn=root.findViewById(R.id.uploadProfilePicture);
//        profilePictureBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"Upload Clicked",Toast.LENGTH_LONG).show();
//            }
//        });
//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getApplicationContext(),"Settings Clicked",Toast.LENGTH_LONG).show();
//                Intent settingsIntent=new Intent(getActivity(), SettingsActivity.class);
//                startActivity(settingsIntent);
//            }
//        });
        return root;
    }
}