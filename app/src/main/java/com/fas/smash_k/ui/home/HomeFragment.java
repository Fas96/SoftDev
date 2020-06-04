package com.fas.smash_k.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.fas.smash_k.R;
import com.fas.smash_k.TalkActivity;
import com.fas.smash_k.ui.adaptors.ConversationsAdapter;
import com.fas.smash_k.ui.models.chatItems.ItemConversation;
import com.fas.smash_k.ui.models.chatItems.ItemMessages;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Cookie;

public class HomeFragment extends Fragment implements View.OnClickListener {

    //

    public static final int REQUEST_CODE = 11;
    public static final int RESULT_CODE = 12;
    public static final String EXTRA_KEY_TEST = "testKey";
    int conversationPosition;
    //Home Conversation Adapter
    ArrayList<ItemConversation> conversations;
    ConversationsAdapter conversationsAdapter;

    HomeFragment conversationsFragment;
    FragmentManager fragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button btn_new = (Button) root.findViewById(R.id.btn_newActivity_id);
        btn_new.setOnClickListener(this);



        ///
        fragmentManager = getFragmentManager();
        //ScrollView scroll_view = (ScrollView) findViewById(R.id.scroll_view_id);
        //recycler view
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.conversation);
        System.out.println("Fas");
        this.conversations = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int days = i + 10;
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append("/");
            sb.append(days);
            sb.append("/2020");
            ItemConversation conversation = new ItemConversation("https://images3.alphacoders.com/823/82317.jpg", "Bhim", "whats boiling down", sb.toString(), i);
            this.conversations.add(conversation);
        }
        conversationsAdapter = new ConversationsAdapter(conversations, getActivity());
        recyclerView.setAdapter(conversationsAdapter);
        return root;
    }

    // Initializing and starting the second Activity
    private void startSecondActivity() {
        Intent intent = new Intent(getActivity(), TalkActivity.class);
        startActivityForResult(intent, HomeFragment.REQUEST_CODE);
    }


    //top button to convo
    @Override
    public void onClick(View v) {
        Intent send_intent = new Intent(getActivity(), TalkActivity.class);
        getActivity().startActivity(send_intent);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("I was called ");
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            String testResult = data.getStringExtra(EXTRA_KEY_TEST);
            int conversationPosition = data.getIntExtra(ConversationsAdapter.CONVENSATIONPOSTON, -1);
            if (conversationPosition > -1) {
                ((ItemConversation) this.conversations.get(conversationPosition)).setUnreadCount(0);
                this.conversationsAdapter.notifyItemChanged(conversationPosition);
            }


        }
    }


}