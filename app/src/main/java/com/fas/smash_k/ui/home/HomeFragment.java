package com.fas.smash_k.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class HomeFragment extends Fragment implements View.OnClickListener {

    int conversationPosition;
    //Home Conversation Adapter
    ArrayList<ItemConversation> conversations;
    ConversationsAdapter conversationsAdapter;

    private HomeViewModel homeViewModel;

    //head
    public static final int REQUESTCODE_CONVERSATION_POSITION = 1500;
    public static final String SHARED_PREF_USER_LOGGED_IN = "user_logged_in";

    HomeFragment conversationsFragment;
    FragmentManager fragmentManager = getFragmentManager();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button btn_new = (Button) root.findViewById(R.id.btn_newActivity_id);
        btn_new.setOnClickListener(this);

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




    //top button to convo
    @Override
    public void onClick(View v) {
        Intent send_intent = new Intent(getActivity(), TalkActivity.class);
        getActivity().startActivity(send_intent);
    }

    public void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code,result_code,data);
        System.out.println("Fas fax");
        if (result_code == Activity.RESULT_OK && request_code == 1500 && data != null && data.hasExtra(ConversationsAdapter.CONVENSATIONPOSTON)) {
            int conversationPosition = data.getIntExtra(ConversationsAdapter.CONVENSATIONPOSTON, -1);
            if (conversationPosition > -1) {
                ((ItemConversation) this.conversations.get(conversationPosition)).setUnreadCount(0);
                this.conversationsAdapter.notifyItemChanged(conversationPosition);
            }
        }
    }

}